package com.ltldev.shop.services;

import com.ltldev.shop.dto.CategoryDTO;
import com.ltldev.shop.models.Category;

import java.util.List;

public interface ICategoryService {
    Category crateCategory(CategoryDTO category);

   Category getCategory(long categoryId);

    List<Category> getCategories();

    Category updateCategory(long categoryId, CategoryDTO category);

    void deleteCategory(long categoryId);

}
