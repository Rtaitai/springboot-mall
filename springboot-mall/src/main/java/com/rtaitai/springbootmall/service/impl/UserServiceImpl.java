package com.rtaitai.springbootmall.service.impl;

import com.rtaitai.springbootmall.dao.UserDao;
import com.rtaitai.springbootmall.dto.UserRegisterRequest;
import com.rtaitai.springbootmall.model.User;
import com.rtaitai.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {

        return userDao.createUser(userRegisterRequest);
    }
}
