package com.ltldev.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProductImageDTO {

//    @Min(value = 1)
    @JsonProperty("product_id")
    private Long productId;
    @Size(min =5, max = 200, message = "Image's name")
    @JsonProperty("img_url")
    private  String imgUrl;
}
