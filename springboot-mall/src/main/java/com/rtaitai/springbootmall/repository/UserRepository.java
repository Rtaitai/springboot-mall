package com.rtaitai.springbootmall.repository;

import com.rtaitai.springbootmall.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserByUserId(Integer userId);

    Optional<User> findUserByEmail(String email);
}
