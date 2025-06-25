package com.rtaitai.springbootmall.service.impl;

import com.rtaitai.springbootmall.cache.RedisCache;
import com.rtaitai.springbootmall.constant.CacheNames;
import com.rtaitai.springbootmall.entity.User;
import com.rtaitai.springbootmall.repository.UserRepository;
import com.rtaitai.springbootmall.request.UserChangePasswordRequest;
import com.rtaitai.springbootmall.request.UserLoginRequest;
import com.rtaitai.springbootmall.request.UserRegisterRequest;
import com.rtaitai.springbootmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final String SALT = "salt";

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserById(Integer userId) {
        return userRepository.findUserByUserId(userId);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {

        // 檢查註冊的 email
        Optional<User> user = userRepository.findUserByEmail(userRegisterRequest.getEmail());

        if (user.isPresent()) {
            log.warn("該 Email {} 已經被註冊過",userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 使用 MD5 生成密碼的雜湊值
        String hashedPassword = DigestUtils.md5DigestAsHex((userRegisterRequest.getPassword() + SALT).getBytes());
        userRegisterRequest.setPassword(hashedPassword);

        User newUser = new User();
        newUser.setEmail(userRegisterRequest.getEmail());
        newUser.setPassword(userRegisterRequest.getPassword());
        newUser.setCreatedDate(LocalDateTime.now());
        newUser.setLastModifiedDate(LocalDateTime.now());
        return userRepository.save(newUser).getUserId();
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {
        User user = userRepository.findUserByEmail(userLoginRequest.getEmail())
                .orElseThrow(() -> {
                    log.warn("該 Email {} 尚未註冊", userLoginRequest.getEmail());
                    return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email 未注册");
                });

        // 使用 MD5 生成密碼的雜湊值
        String hashedPassword = DigestUtils.md5DigestAsHex((userLoginRequest.getPassword() + SALT).getBytes());
        String userId = user.getUserId().toString();

        try {

            // 处于锁定状态，可直接抛异常
            if (!redisCache.putIfAbsent(CacheNames.LOGIN_LOCK, userId, "occupied", Duration.ofSeconds(2))) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "登录太频繁，请稍后重试");
            }

            if (!user.getPassword().equals(hashedPassword)) {
                long times = redisCache.increment(CacheNames.LOGIN_FAILED_TIMES, userId, Duration.ofMinutes(2));
                log.warn("該 Email {} 密碼不正確", userLoginRequest.getEmail());
                // 超过次数后可另抛不同信息
                if (times >= 5) {
                    redisCache.put(CacheNames.LOGIN_LOCK, userId, "occupied", Duration.ofSeconds(90));
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "密码错误次数过多，请 90 秒后重试");
                }
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "密码不正确");
            }

            // 验证成功后，可以清掉失败次数
            redisCache.evict(CacheNames.LOGIN_FAILED_TIMES, userId);
            return user;

        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception e) {
            log.error("login_fail, loginEmail:{}", userLoginRequest.getEmail(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "登录失败，请稍后重试");
        }
    }

    @Override
    public User updateUserPassword(UserChangePasswordRequest userChangePasswordRequest) {

        User user = userRepository.findUserByEmail(userChangePasswordRequest.getEmail())
                .orElseThrow(() -> {
                    log.warn("該 Email {} 尚未註冊", userChangePasswordRequest.getEmail());
                    return new ResponseStatusException(HttpStatus.BAD_REQUEST);
                });

        String hashedPassword = DigestUtils.md5DigestAsHex((userChangePasswordRequest.getPassword() + SALT).getBytes());

        user.setPassword(hashedPassword);
        user.setLastModifiedDate(LocalDateTime.now());
        return userRepository.save(user);

    }
}
