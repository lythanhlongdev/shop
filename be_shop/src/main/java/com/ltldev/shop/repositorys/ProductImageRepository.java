package com.ltldev.shop.repositorys;

import com.ltldev.shop.models.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface  ProductImageRepository extends JpaRepository<ProductImage,Long>{

    List<ProductImage> findByProductId(Long productId);
}
