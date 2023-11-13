package com.ltldev.shop.models;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "users")
public class User extends BaseLong implements UserDetails{


    @Column(name = "fullname", length = 100)
    private String fullName;

    @Column(name = "phone_number", length = 20, nullable = false)
    private String phoneNumber;

    @Column(name = "address", length = 200, nullable = false)
    private String address;

    @Column(name = "password_", length = 100, nullable = false)
    private String password;

    @Column(name = "is_active")
    private Long isActive;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "facebook_account_id")
    private Long facebookAccountId;

    @Column(name = "google_account_id")
    private Long googleAccountId;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "created_at")
    private LocalDateTime createAt;
    @Column(name = "updated_at")
    private LocalDateTime updateAt;

    @PrePersist
    protected void onCreate() {
        createAt = LocalDateTime.now();
        updateAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updateAt = LocalDateTime.now();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // lây quyền
        String roleName = this.getRole().getName();
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
//        authorityList.add(new SimpleGrantedAuthority("ROLE_" + roleName));
//        authorityList.add(new SimpleGrantedAuthority("ADMIN"));
        authorityList.add(new SimpleGrantedAuthority("USER"));
        return authorityList;
    }

    @Override
    public String getUsername() {
        return phoneNumber;// truong dang nhap
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
