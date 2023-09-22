package com.ltldev.shop.models;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "order_details")
public class OrderDetail extends BaseLong{

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order orderId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product productId;

    private Float price;

    @Column(name = "number_of_products")
    private Long numberOfProducts;

    @Column(name = "total_money")
    private  Float totalMoney;

    private  String color;
}
