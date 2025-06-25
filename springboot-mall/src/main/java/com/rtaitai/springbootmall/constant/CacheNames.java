package com.rtaitai.springbootmall.constant;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class CacheNames {

    // 登入
    public static final String LOGIN_LOCK = "login_lock";
    public static final String LOGIN_FAILED_TIMES = "login_failed_times";

}
