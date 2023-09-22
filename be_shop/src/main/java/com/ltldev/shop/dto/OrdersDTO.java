package com.ltldev.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ltldev.shop.enums.Payments;
import com.ltldev.shop.enums.ShippingMethod;
import com.ltldev.shop.models.Order;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDTO {

    @Min(value = 1, message = "user id  must be greater than or equal to 1")
    @NotNull(message = "user id is required")
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("fullname")
    private String fullName;
    @Size(min = 5, message = "phone  must be greater than or equal to 5")
    @NotBlank(message = "phone number is required")
    @JsonProperty("phone_number")
    private String phoneNumber;
    @NotBlank(message = "email is required")
    @JsonProperty("email")
    private String email;
    @NotBlank(message = "address is required")
    private String address;
    private String note;
    @Min(value = 0, message = "total money  must be greater than or equal to 0")
    @JsonProperty("total_money")
    private Float totalMoney;
    @NotBlank(message = "shipping method is required")
    @JsonProperty("shipping_method")
    private String shippingMethod;
    @JsonProperty("status")
    private String status;
    //    @NotBlank(message = "shipping date is required")
    @JsonProperty("shipping_date")
    private LocalDate shippingDate;
    @JsonProperty("shipping_address")
    private String shippingAddress;
    @NotBlank(message = "payment method is required")
    @JsonProperty("payment_method")
    private String paymentMethod;


}
