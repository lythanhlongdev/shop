package com.ltldev.shop.services;

import com.ltldev.shop.dto.ProductDTO;
import com.ltldev.shop.dto.ProductImageDTO;
import com.ltldev.shop.exception.DataNotFoundException;
import com.ltldev.shop.models.Product;
import com.ltldev.shop.models.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface IProductService {

    Product createProduct(ProductDTO productDTO) throws Exception;

    Product getProductById(Long productId) throws Exception;

    Page<Product> getAllProducts(PageRequest pageRequest);

    Product updateProduct(Long productId, ProductDTO productDTO) throws Exception;

    boolean exitsByName(String name);

    ProductImage ceateProductImage(Long productId, ProductImageDTO imageDTO) throws Exception;

    void deleteProduct(Long id);

}
