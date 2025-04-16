package com.rtaitai.springbootmall.controller;

import com.rtaitai.springbootmall.request.UserLoginRequest;
import com.rtaitai.springbootmall.request.UserRegisterRequest;
import com.rtaitai.springbootmall.request.UserChangePasswordRequest;
import com.rtaitai.springbootmall.entity.User;
import com.rtaitai.springbootmall.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/users/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {
        Integer userId = userService.register(userRegisterRequest);

        User user = userService.getUserById(userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/users/login")
    public ResponseEntity<User> login(@RequestBody @Valid UserLoginRequest userLoginRequest) {

        User user = userService.login(userLoginRequest);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping("/users/update-password")
    public ResponseEntity<User> updateUserPassword(@RequestBody @Valid UserChangePasswordRequest userChangePasswordRequest) {

        User user = userService.updateUserPassword(userChangePasswordRequest);

        // return ResponseEntity.status(HttpStatus.OK).body(user); 跟下面一样但下面的比较简洁
        return ResponseEntity.ok(user);
    }

}
