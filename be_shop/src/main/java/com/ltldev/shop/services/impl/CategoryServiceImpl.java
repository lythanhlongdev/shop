package com.ltldev.shop.services.impl;

import com.ltldev.shop.dto.CategoryDTO;
import com.ltldev.shop.models.Category;
import com.ltldev.shop.repositorys.CategoryRepository;
import com.ltldev.shop.services.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements ICategoryService {

    private final CategoryRepository repository;

    @Override
    public Category crateCategory(CategoryDTO categoryDTO) {
        // conver dto -> object
        Category newCategory = Category.builder().name(categoryDTO.getName()).build();
        return repository.save(newCategory);
    }

    @Override
    public Category getCategory(long categoryId) {
        return repository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Override
    public List<Category> getCategories() {
        return repository.findAll();
    }

    @Override
    public Category updateCategory(long categoryId, CategoryDTO categoryDTO) {
        Category existingCategory = getCategory(categoryId);
        existingCategory.setName(categoryDTO.getName());
        return existingCategory;
    }

    @Override
    public void deleteCategory(long categoryId) {
        repository.deleteById(categoryId);
    }
}
