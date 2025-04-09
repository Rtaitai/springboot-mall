package com.rtaitai.springbootmall.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserChangePasswordRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
