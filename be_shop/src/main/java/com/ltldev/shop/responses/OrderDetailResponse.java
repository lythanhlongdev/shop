package com.ltldev.shop.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ltldev.shop.models.OrderDetail;
import lombok.*;


@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponse {
    private Long id;
    @JsonProperty("order_id")
    private Long orderId;
    @JsonProperty("product_id")
    private Long productId;
    private Float price;
    @JsonProperty("number_of_products")
    private Long numberOfProducts;
    @JsonProperty("total_money")
    private Float totalMoney;
    private String color;

    private String productName;

    public static OrderDetailResponse mapperOrderDetail(OrderDetail orderDetail) {
        return OrderDetailResponse.builder()
                .id(orderDetail.getId())
                .orderId(orderDetail.getOrderId().getId())
                .productId(orderDetail.getProductId().getId())
                .productName(orderDetail.getProductId().getName())
                .price(orderDetail.getPrice())
                .numberOfProducts(orderDetail.getNumberOfProducts())
                .totalMoney(orderDetail.getTotalMoney())
                .color(orderDetail.getColor())
                .build();
    }
}
