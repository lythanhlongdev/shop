package com.ltldev.shop.repositorys;
import com.ltldev.shop.models.Product;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
    boolean existsByName(String title);
    Page<Product> findAllBy(Pageable pageable);// phan trang
}
