package com.ltldev.shop.repositorys;

import com.ltldev.shop.models.Order;
import com.ltldev.shop.models.OrderDetail;
import com.ltldev.shop.responses.OrderDetailResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {
    List<OrderDetail> findByOrderId(Order order);
}
