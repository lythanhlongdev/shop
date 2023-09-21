package com.ltldev.shop.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "social_account")
public class SocialAccount extends BaseLong {

    @Column(name = "provider", length = 50)
    private String provider;
    @Column(name = "provider_id", length = 10)
    private String providerId;
    @Column(name = "email", length = 100)
    private String email;
    @Column(name = "name", length = 100)
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
