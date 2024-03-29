package com.rtaitai.springbootmall.dao;

import com.rtaitai.springbootmall.dto.OrderQueryParams;
import com.rtaitai.springbootmall.dto.UserRegisterRequest;
import com.rtaitai.springbootmall.model.User;

public interface UserDao {

    User getUserById(Integer userId);

    User getUserByEmail(String email);

    Integer createUser(UserRegisterRequest userRegisterRequest);

}
