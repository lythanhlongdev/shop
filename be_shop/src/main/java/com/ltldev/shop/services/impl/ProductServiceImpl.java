package com.ltldev.shop.services.impl;

import com.ltldev.shop.dto.ProductDTO;
import com.ltldev.shop.dto.ProductImageDTO;
import com.ltldev.shop.exception.DataNotFoundException;
import com.ltldev.shop.exception.InvalidParamException;
import com.ltldev.shop.models.Category;
import com.ltldev.shop.models.Product;
import com.ltldev.shop.models.ProductImage;
import com.ltldev.shop.repositorys.CategoryRepository;
import com.ltldev.shop.repositorys.ProductImageRepository;
import com.ltldev.shop.repositorys.ProductRepository;
import com.ltldev.shop.services.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    public final CategoryRepository categoryRepository;
    public final ProductImageRepository productImageRepository;

    @Override
    public Product createProduct(ProductDTO productDTO) throws Exception {
        // check category
        Category existingCategory = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("cannot find category not fount id: " + productDTO.getCategoryId()));
        // convert DTO => Product
        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .thumbnail(productDTO.getThumbnail())
                .description(productDTO.getDescription())
                .category(existingCategory)
                .build();
        return productRepository.save(newProduct);
    }

    @Override
    public Product getProductById(Long productId) throws Exception {
        return productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException("cannot find product not found id: " + productId));
    }

    @Override
    public Page<Product> getAllProducts(PageRequest pageRequest) {
        // get number of product for page and limit
        return productRepository.findAll(pageRequest);
    }

    @Override
    public Product updateProduct(Long productId, ProductDTO productDTO) throws Exception {
        // get and check product
        Product exitstingProduct = getProductById(productId);
        // get and check category
        Category existingCategory = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("cannot find category not fount id: " + productDTO.getCategoryId()));
        // convert
        exitstingProduct.setName(productDTO.getName());
        exitstingProduct.setPrice(productDTO.getPrice());
        exitstingProduct.setThumbnail(productDTO.getThumbnail());
        exitstingProduct.setCategory(existingCategory);
        exitstingProduct.setDescription(productDTO.getDescription());
        return productRepository.save(exitstingProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
//        if (optionalProduct.isPresent()){
//            productRepository.delete(optionalProduct.get());
//        }
        // delete
        optionalProduct.ifPresent(productRepository::delete);
    }


    // input img
    @Override
    public boolean exitsByName(String name) {
        return productRepository.existsByName(name);
    }


    @Override
    public ProductImage ceateProductImage(Long productId, ProductImageDTO imageDTO) throws Exception {

        Product exitstingProduct = productRepository
                .findById(productId)
                .orElseThrow(() -> new DataNotFoundException("cannot find product not found id: " + productId));

        // convert
        ProductImage newProductImage = ProductImage.builder()
                .product(exitstingProduct)
                .imgUrl(imageDTO.getImgUrl())
                .build();
        // input max 5 img, get size > 5 -> throw
        List<ProductImage> pr = productImageRepository.findByProductId(exitstingProduct.getId());
        int size = pr.size();
        if (size >= 5) {
            throw  new InvalidParamException("Number of image must be <= 5");
        }
        return productImageRepository.save(newProductImage);
    }
}
