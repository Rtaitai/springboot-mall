package com.rtaitai.springbootmall.service.impl;

import com.rtaitai.springbootmall.dao.UserDao;
import com.rtaitai.springbootmall.request.UserChangePasswordRequest;
import com.rtaitai.springbootmall.request.UserLoginRequest;
import com.rtaitai.springbootmall.request.UserRegisterRequest;
import com.rtaitai.springbootmall.model.User;
import com.rtaitai.springbootmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {

    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final String SALT = "salt";

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {

        // 檢查註冊的 email
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());

        if (user != null) {
            log.warn("該 Email {} 已經被註冊過",userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 使用 MD5 生成密碼的雜湊值
        String hashedPassword = DigestUtils.md5DigestAsHex((userRegisterRequest.getPassword() + SALT).getBytes());
        userRegisterRequest.setPassword(hashedPassword);

        // 創建帳號
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {
        User user = userDao.getUserByEmail(userLoginRequest.getEmail());

        // 檢查 user 是否存在
        if (user == null) {
            log.warn("該 Email {} 尚未註冊", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 使用 MD5 生成密碼的雜湊值
        String hashedPassword = DigestUtils.md5DigestAsHex((userLoginRequest.getPassword() + SALT).getBytes());

        // 比較密碼
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        } else {
            log.warn("該 Email {} 密碼不正確", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public User updateUserPassword(UserChangePasswordRequest userChangePasswordRequest) {

        User user = userDao.getUserByEmail(userChangePasswordRequest.getEmail());

        // 檢查 user 是否存在
        if (user == null) {
            log.warn("該 Email {} 尚未註冊", userChangePasswordRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        String hashedPassword = DigestUtils.md5DigestAsHex((userChangePasswordRequest.getPassword() + SALT).getBytes());

        userDao.updateUserPassword(user.getEmail(), hashedPassword);

        return userDao.getUserByEmail(userChangePasswordRequest.getEmail());

    }
}
