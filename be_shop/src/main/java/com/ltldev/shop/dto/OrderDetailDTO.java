package com.ltldev.shop.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {

    @NonNull
    @JsonProperty("order_id")
    private Long orderId;
    @NonNull
    @JsonProperty("product_id")
    private Long productId;

    @NotNull
    @Min(value = 0, message = "price  must be greater than or equal to 0")
    private Float price;
    @Min(value = 1, message = "number of products  must be greater than or equal to 1")
    @JsonProperty("number_of_products")
    private Long numberOfProducts;
    @Min(value = 0, message = "total money must be greater than or equal to 0")
    @JsonProperty("total_money")
    private Float totalMoney;
    private String color;
}
