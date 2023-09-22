package com.ltldev.shop.services;

import com.ltldev.shop.dto.OrdersDTO;
import com.ltldev.shop.models.Order;

import java.util.List;

public interface IOrderService {

    Order creatOrder(OrdersDTO ordersDTO) throws Exception;
    Order getByOrderId(Long orderId) throws Exception;
    Order  updateOrder(Long orderId, OrdersDTO ordersDTO) throws  Exception;
    List<Order> getAllOrderByUserId(Long userId) throws Exception;
    void deleteOrder(Long orderId) throws Exception;

}
