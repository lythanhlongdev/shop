package com.ltldev.shop.dto.user;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.sql.Date;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @JsonProperty("fullname")
    private String fullName;
    @NotBlank(message = "phone number is required")
    @JsonProperty("phone_number")
    private String phoneNumber;
    @NotBlank(message = "address is required")
    private String address;
    @NotBlank(message = "Password cant not be blank")
    @JsonProperty("password_")
    private String password;
    @JsonProperty("retype_password")
    private String retypePassword;
    @JsonProperty("date_of_birth")
    private Date dateOfBirth;
    @JsonProperty("facebook_account_id")
    private Integer facebookAccountId;
    @JsonProperty("google_account_id")
    private Integer googleAccountId;
    @NotNull(message = "Role id is required")
    @JsonProperty("role_id")
    private Integer roleId;
}
