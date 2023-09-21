package com.ltldev.shop.controllers;

import com.ltldev.shop.dto.CategoryDTO;
import com.ltldev.shop.models.Category;
import com.ltldev.shop.services.ICategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    private final ICategoryService service;

    @PostMapping("")
    public ResponseEntity<?> crateCategory(@RequestBody @Valid CategoryDTO request, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errMessage = result.getAllErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            return ResponseEntity.badRequest().body(errMessage);
        }
        return ResponseEntity.ok("insert category success " + service.crateCategory(request));
    }

    @GetMapping("") // http://localhost:8080/api/v1/categories?page=1&limit=10
    public ResponseEntity<String> getCategories(@RequestParam("page") int page, @RequestParam("limit") int limit) {
        List<Category> categories = service.getCategories();
        return ResponseEntity.ok(""+categories);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategory(@PathVariable long id) {
        try {
            return ResponseEntity.ok("findById id = " + service.getCategory(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable long id) {
        try {
            service.deleteCategory(id);
            return ResponseEntity.ok("Delete Category successfully "+id);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
