package com.ltldev.shop.responses;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponses {
    private List<ProductResponse> productResponses;
    private int totalPage;
}
