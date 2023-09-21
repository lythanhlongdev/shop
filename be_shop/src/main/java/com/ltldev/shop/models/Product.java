package com.ltldev.shop.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product extends BaseLong {

    @Column(name = "name", nullable = false, length = 350)
    private String name;

    @Column
    private Float price;

    @Column(name = "thumbnail", nullable = true, length = 300)
    private String thumbnail;

    @Column(name = "description", nullable = true, length = 300)
    private String description;


    @Column(name = "created_at")
    private LocalDateTime createAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @PrePersist
    protected void onCreate() {
        createAt = LocalDateTime.now();
        updateAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updateAt = LocalDateTime.now();
    }

}
