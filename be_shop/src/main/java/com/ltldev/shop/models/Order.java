package com.ltldev.shop.models;

import com.ltldev.shop.enums.Payments;
import com.ltldev.shop.enums.ShippingMethod;
import com.ltldev.shop.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseLong {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @Column(name = "fullname", length = 100)
    private String fullName;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Column(name = "phone_number", length = 20, nullable = false)
    private String phoneNumber;

    @Column(name = "adderss", length = 255, nullable = false)
    private String address;

    private String note;

    @Column(name = "order_date")
    private LocalDateTime orderDate;
    @Column(name = "total_money")
    private Float totalMoney;

    @Column(name = "shipping_address", length = 100, nullable = false)
    private String shippingAddress;

    @Column(name = "shipping_method", nullable = false)
    private ShippingMethod shippingMethod;

    @Column(name = "shipping_date", nullable = false)
    private Date shippingDate;

    @Column(name = "payment_method", nullable = false)
    private Payments paymentMethod;

    @Column(name = "status")
    private Status status;

    @Column(name = "active", nullable = false)
    private Boolean active; // admin

    @PrePersist
    protected void onCreate() {
        orderDate = LocalDateTime.now();
    }
}
