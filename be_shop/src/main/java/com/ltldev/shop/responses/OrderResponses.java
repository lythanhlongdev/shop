package com.ltldev.shop.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ltldev.shop.models.Order;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponses {

    @JsonProperty("user_id")
    private Integer userid;
    @JsonProperty("fullname")
    private String fullName;
    @JsonProperty("phone_number")
    private String phoneNumber;
    private String email;
    private String address;
    private String note;
    @JsonProperty("total_money")
    private float totalMoney;
    @JsonProperty("shipping_method")
    private String shippingMethod;
    @JsonProperty("payment_method")
    private String payment_method;
    @JsonProperty("shipping_address")
    private String shippingAddress;

}
