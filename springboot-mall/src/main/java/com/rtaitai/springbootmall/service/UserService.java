package com.rtaitai.springbootmall.service;

import com.rtaitai.springbootmall.dto.UserRegisterRequest;
import com.rtaitai.springbootmall.model.User;

public interface UserService {

    User getUserById(Integer userId);

    Integer register(UserRegisterRequest userRegisterRequest);
}
