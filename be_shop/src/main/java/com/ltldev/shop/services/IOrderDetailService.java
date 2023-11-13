package com.ltldev.shop.services;

import com.ltldev.shop.dto.OrderDetailDTO;
import com.ltldev.shop.models.OrderDetail;
import com.ltldev.shop.responses.OrderDetailResponse;

import java.util.List;

public interface IOrderDetailService {

    OrderDetailResponse creatOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception;
    OrderDetailResponse getByIdOrderDetail(Long orderDetailId) throws Exception;
    List<OrderDetail> getAllOrderById(Long orderId) throws Exception;
    OrderDetailResponse updateOrderDetail(Long orderDetailId, OrderDetailDTO orderDetailDTO) throws  Exception;
    void deleteOrderDetail(Long orderId) throws Exception;
}
