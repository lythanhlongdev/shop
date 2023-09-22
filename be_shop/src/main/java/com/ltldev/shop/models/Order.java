package com.ltldev.shop.models;

import com.ltldev.shop.enums.Payments;
import com.ltldev.shop.enums.ShippingMethod;
import com.ltldev.shop.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @Column(name = "address", length = 255, nullable = false)
    private String address;
    private String note;

    @Column(name = "order_date")
    private LocalDateTime orderDate;
    @Column(name = "total_money")
    private Float totalMoney;

    @Column(name = "shipping_address", length = 100, nullable = false)
    private String shippingAddress;

    @Column(name = "shipping_date", nullable = false)
    private LocalDate shippingDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "shipping_method", nullable = false)
    private ShippingMethod shippingMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private Payments paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "tracking_number")
    private String trackingNumber;

    @Column(name = "active", nullable = false)
    private Boolean active; // admin

    @PrePersist
    protected void onCreate() {
        orderDate = LocalDateTime.now();
    }
}
