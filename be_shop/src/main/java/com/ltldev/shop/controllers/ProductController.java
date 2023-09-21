package com.ltldev.shop.controllers;

import com.ltldev.shop.dto.ProductDTO;
import com.ltldev.shop.dto.ProductImageDTO;
import com.ltldev.shop.models.Product;
import com.ltldev.shop.models.ProductImage;
import com.ltldev.shop.services.IProductService;
import com.ltldev.shop.utils.FileUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final IProductService productService;

    @GetMapping("") // http://localhost:8080/api/v1/products?page=1&limit=10
    public ResponseEntity<String> getProducts(@RequestParam("page") int page, @RequestParam("limit") int limit) {
        return ResponseEntity.ok(String.format("get all product  page = %d, limit= %d", page, limit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getProduct(@PathVariable String id) {
        try {
            return ResponseEntity.ok("get product by id = " + id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

//    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<?> save(@RequestBody @Valid ProductDTO req , BindingResult result
//    ) {
//        try {
//            // check Data
//            if (result.hasErrors()) {
//                List<String> errMessage = result.getAllErrors()
//                        .stream()
//                        .map(fieldError -> fieldError.getDefaultMessage())
//                        .toList();
//                return ResponseEntity.badRequest().body(errMessage);
//            }
//            // get file in DTO  request
//            MultipartFile file = req.getFile();
//            if (file != null) {
//                // check file img, if img > 10 MB
//                if (file.getSize() > 10 * 1024 * 1024) {
//                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File to large ! max size is 10MB");
//                }
//                String contentType = file.getContentType(); // check name
//                if (contentType == null || !contentType.startsWith("img/")) {
//                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("File must be an image");
//                }
//                String fileName = storeFile(file);// save file or change file
//                // save database
//
//            }
//            // return ok if pass check
//            return ResponseEntity.ok("insert product success " + req);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Insert product Error " + e.getMessage());
//        }
//    }

    @PostMapping("")
    public ResponseEntity<?> createProduct(@RequestBody @Valid ProductDTO request, BindingResult result) {
        try {
            // kiểm tra dữ liệu
            if (result.hasErrors()) {
                List<String> errMessage = result.getAllErrors()
                        .stream()
                        .map(fieldError -> fieldError.getDefaultMessage())
                        .toList();
                return ResponseEntity.badRequest().body(errMessage);
            }
            // save product
            Product newProduct = productService.createProduct(request);
            // trả về ok nếu vượt qua kiểm tra
            return ResponseEntity.ok("insert product success " + newProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/uploads/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImgs(@Valid @PathVariable("productId") Long productId, @ModelAttribute List<MultipartFile> files) {
        try {
            // user no upload file files.size = 1 =>  null, check
            List<ProductImage> listImg = new ArrayList<>();
            Product existingProduct = productService.getProductById(productId);
            // neu co anh moi luu
            if (files != null && files.size() != 0) {
                for (MultipartFile file : files) {
                    // kiểm tra tệp img, nếu img > 10 MB
                    if (file.getSize() > 10 * 1024 * 1024) {
                        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File to large ! max size is 10MB");
                    }
                    // kiểm tra loại tệp, phải là hình ảnh
                    String contentType = file.getContentType();
                    if (contentType == null || contentType.startsWith("img/")) {
                        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("File must be an image" + contentType);
                    }
                    // lưu tệp hoặc thay đổi tệp
                    String fileName = FileUtil.saveImg(file);

                    // mapping url and save img
                    ProductImageDTO productImageDTO = ProductImageDTO.builder().productId(existingProduct.getId()).imgUrl(fileName).build();
                    ProductImage productImg = productService.ceateProductImage(existingProduct.getId(),productImageDTO);
                    listImg.add(productImg);
                }
            }
            return ResponseEntity.ok(listImg);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@Valid @PathVariable Long id, @Valid @PathVariable ProductDTO request) {
        try {
            return ResponseEntity.ok("update product successfully = " + request);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        try {
            return ResponseEntity.ok("Delete product id = " + id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
