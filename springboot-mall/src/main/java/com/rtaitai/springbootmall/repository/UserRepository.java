package com.rtaitai.springbootmall.repository;

import com.rtaitai.springbootmall.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserByUserId(Integer userId);
}
