package com.ltldev.shop.services.impl;

import com.ltldev.shop.dto.OrdersDTO;
import com.ltldev.shop.enums.Payments;
import com.ltldev.shop.enums.ShippingMethod;
import com.ltldev.shop.enums.Status;
import com.ltldev.shop.exception.DataNotFoundException;
import com.ltldev.shop.models.Order;
import com.ltldev.shop.models.User;
import com.ltldev.shop.repositorys.OrderRepository;
import com.ltldev.shop.repositorys.UserRepository;;
import com.ltldev.shop.services.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements IOrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Override
    public Order creatOrder(OrdersDTO ordersDTO) throws Exception {
        // check user
        User exitstingUser = userRepository.findById(ordersDTO.getUserId()).
                orElseThrow(() -> new DataNotFoundException("Cannot find user with id: " + ordersDTO.getUserId()));
        // Convert orderDTO => Order
        Order order = Order.builder()
                .userId(exitstingUser)
                .fullName(ordersDTO.getFullName())
                .phoneNumber(ordersDTO.getPhoneNumber())
                .email(ordersDTO.getEmail())
                .address(ordersDTO.getAddress())
                .totalMoney(ordersDTO.getTotalMoney())
                .note(ordersDTO.getNote())
                .active(true)
                .shippingAddress(ordersDTO.getShippingAddress())
                .build();

        order.setStatus(Status.pending);
        order.setTrackingNumber(" ");
        order.setShippingMethod(ShippingMethod.valueOf(ordersDTO.getShippingMethod()));
        order.setPaymentMethod(Payments.valueOf(ordersDTO.getPaymentMethod()));
        LocalDate shipDate = ordersDTO.getShippingDate() == null ? LocalDate.now() : ordersDTO.getShippingDate();
        if (shipDate.isBefore(LocalDate.now())) {
            throw new DataNotFoundException("Shipping date  >=  date now");
        }
        order.setShippingDate(shipDate);
        orderRepository.save(order);
        return order;
    }

    @Override
    public Order getByOrderId(Long orderId) throws Exception {
        return orderRepository.findById(orderId)
                .orElseThrow(
                        () -> new DataNotFoundException
                                ("Cannot find order with id: " + orderId));
    }

    @Override
    public List<Order> getAllOrderByUserId(Long userId) throws Exception {
        User exitstingUser = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException
                        ("Cannot find order not found with userId: " + userId));
        return orderRepository.findByUserId(exitstingUser);
    }


    // for admin
    @Override
    public Order updateOrder(Long orderId, OrdersDTO ordersDTO) throws Exception {
        // check order
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Cannot find order with id: " + orderId));
        // check user
        User exitstingUser = userRepository.findById(ordersDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find user with id: " + ordersDTO.getUserId()));
        // Convert orderDTO => Order
        order.setUserId(exitstingUser);

        order.setFullName(ordersDTO.getFullName());
        order.setPhoneNumber(ordersDTO.getPhoneNumber());
        order.setEmail(ordersDTO.getEmail());
        order.setAddress(ordersDTO.getAddress());
        order.setTotalMoney(ordersDTO.getTotalMoney());
        order.setNote(ordersDTO.getNote());
        order.setShippingAddress(ordersDTO.getShippingAddress());
        order.setActive(true);
        order.setShippingAddress(ordersDTO.getShippingAddress());
        // check status
        String status = ordersDTO.getStatus() != null ? ordersDTO.getStatus() : "pending";
        order.setStatus(Status.valueOf(status));
        order.setTrackingNumber(" ");
        order.setShippingMethod(ShippingMethod.valueOf(ordersDTO.getShippingMethod()));
        order.setPaymentMethod(Payments.valueOf(ordersDTO.getPaymentMethod()));
        LocalDate shipDate = ordersDTO.getShippingDate() == null ? LocalDate.now() : ordersDTO.getShippingDate();
        if (shipDate.isBefore(LocalDate.now())) {
            throw new DataNotFoundException("Shipping date  >=  date now");
        }
        order.setShippingDate(shipDate);

        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long orderId) throws Exception {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(
                        () -> new DataNotFoundException
                                ("Cannot delete order with id: " + orderId));
        existingOrder.setActive(false);
        orderRepository.save(existingOrder);
    }


}
