package com.ltldev.shop.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    @NotBlank(message = "phone number is required")
    @JsonProperty("phone_number")
    private String phoneNumber;
    @NotBlank(message = "Password cant not be blank")
    @JsonProperty("password_")
    private String password;
}
