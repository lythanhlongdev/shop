package com.ltldev.shop.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryDTO {

    @NotEmpty(message = "category no name ")
    String name;
}
