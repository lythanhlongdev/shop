package com.ltldev.shop.repositorys;

import com.ltldev.shop.models.Order;
import com.ltldev.shop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(User userId);
}
