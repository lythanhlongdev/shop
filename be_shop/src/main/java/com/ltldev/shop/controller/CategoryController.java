package com.ltldev.shop.controller;
import com.ltldev.shop.dto.CategoryDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

    @GetMapping("") // http://localhost:8080/api/v1/categories?page=1&limit=10
    public ResponseEntity<String> getCategories(@RequestParam("page") int page, @RequestParam("limit") int limit) {
        return ResponseEntity.ok(String.format("findAL l Category  page = %d, limit= %d", page, limit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getCategory(@PathVariable String id) {
        return ResponseEntity.ok("findById id = " + id);
    }

    @PostMapping("")
    public ResponseEntity<?> crateCategory(@RequestBody @Valid CategoryDTO req, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errMessage = result.getAllErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            return ResponseEntity.badRequest().body(errMessage);
        }
        return ResponseEntity.ok("insert category success " + req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable String id) {
        return ResponseEntity.ok("Delete");
    }
}
