package com.ltldev.shop.services.impl;

import com.ltldev.shop.dto.OrderDetailDTO;
import com.ltldev.shop.exception.DataNotFoundException;
import com.ltldev.shop.models.Order;
import com.ltldev.shop.models.OrderDetail;
import com.ltldev.shop.models.Product;
import com.ltldev.shop.repositorys.OrderDetailRepository;
import com.ltldev.shop.repositorys.OrderRepository;
import com.ltldev.shop.repositorys.ProductRepository;
import com.ltldev.shop.responses.OrderDetailResponse;
import com.ltldev.shop.services.IOrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderDetailServiceImpl implements IOrderDetailService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Override
    public OrderDetailResponse creatOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception {
        Order exitstingOrder = orderRepository
                .findById(orderDetailDTO.getOrderId())
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find Order Detail of Order with OrderId: " + orderDetailDTO.getOrderId()));
        Product exitstingProduct = productRepository
                .findById(orderDetailDTO.getProductId())
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find product of order detail  with productId" + orderDetailDTO.getProductId()));

        // cover DTO => OrderDetail
        OrderDetail newOrderDetail = OrderDetail.builder()
                .orderId(exitstingOrder)
                .productId(exitstingProduct)
                .price(orderDetailDTO.getPrice())// price buy now
                .color(orderDetailDTO.getColor())
                .numberOfProducts(orderDetailDTO.getNumberOfProducts())
                .totalMoney(orderDetailDTO.getTotalMoney())
                .build();

        orderDetailRepository.save(newOrderDetail);
        return OrderDetailResponse.mapperOrderDetail(newOrderDetail);
    }

    @Override
    public OrderDetailResponse getByIdOrderDetail(Long orderDetailId) throws Exception {
        OrderDetail existingOrderDetail = orderDetailRepository.findById(orderDetailId)
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find Order Detail with id: " + orderDetailId));
        return OrderDetailResponse.mapperOrderDetail(existingOrderDetail);
    }

    @Override
    public List<OrderDetail> getAllOrderById(Long orderId) throws Exception {
        Order exitstingOrder = orderRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find Order Detail of Order with OrderId: " + orderId));
        return orderDetailRepository.findByOrderId(exitstingOrder);
    }

    @Override
    public OrderDetailResponse updateOrderDetail(Long orderDetailId, OrderDetailDTO orderDetailDTO) throws Exception {
        OrderDetail exitstingOrderDetail = orderDetailRepository
                .findById(orderDetailId)
                .orElseThrow(() -> new DataNotFoundException("Cannot find Order Detail of Order with id:" + orderDetailId));
        Order exitstingOrder = orderRepository
                .findById(orderDetailDTO.getOrderId())
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find Order Detail of Order with OrderId: " + orderDetailDTO.getOrderId()));
        Product exitstingProduct = productRepository
                .findById(orderDetailDTO.getProductId())
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find product of order detail  with productId" + orderDetailDTO.getProductId()));

        // update use setter not builder because disconnection Entity of database.
        exitstingOrderDetail.setOrderId(exitstingOrder);
        exitstingOrderDetail.setProductId(exitstingProduct);
        exitstingOrderDetail.setPrice(orderDetailDTO.getPrice());
        exitstingOrderDetail.setNumberOfProducts(orderDetailDTO.getNumberOfProducts());
        exitstingOrderDetail.setTotalMoney(orderDetailDTO.getTotalMoney());
        exitstingOrderDetail.setColor(orderDetailDTO.getColor());
        orderDetailRepository.save(exitstingOrderDetail);

        return OrderDetailResponse.mapperOrderDetail(exitstingOrderDetail);
    }


    @Override
    public void deleteOrderDetail(Long orderDetailId) throws Exception {
        Optional<OrderDetail> optionalOrderDetail = orderDetailRepository.findById(orderDetailId);
//        if (optionalProduct.isPresent()){
//            productRepository.delete(optionalProduct.get());
//        }
        // delete
        optionalOrderDetail.ifPresent(orderDetailRepository::delete);
    }
}
