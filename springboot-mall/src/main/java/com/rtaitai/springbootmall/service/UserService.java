package com.rtaitai.springbootmall.service;

import com.rtaitai.springbootmall.request.UserLoginRequest;
import com.rtaitai.springbootmall.request.UserRegisterRequest;
import com.rtaitai.springbootmall.request.UserChangePasswordRequest;
import com.rtaitai.springbootmall.model.User;

public interface UserService {

    User getUserById(Integer userId);

    Integer register(UserRegisterRequest userRegisterRequest);

    User login(UserLoginRequest userLoginRequest);

    User updateUserPassword(UserChangePasswordRequest userChangePasswordRequest);

}
