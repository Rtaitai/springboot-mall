package com.rtaitai.springbootmall.dao;

import com.rtaitai.springbootmall.request.UserRegisterRequest;
import com.rtaitai.springbootmall.model.User;

public interface UserDao {

    User getUserById(Integer userId);

    User getUserByEmail(String email);

    Integer createUser(UserRegisterRequest userRegisterRequest);

    void updateUserPassword(String email, String newPassword);


}
