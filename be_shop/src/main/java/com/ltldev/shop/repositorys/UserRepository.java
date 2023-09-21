package com.ltldev.shop.repositorys;

import com.ltldev.shop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByPhoneNumber(String phoneNumber);// check authen
    Optional<User> findByPhoneNumber(String phoneNumber);
}
