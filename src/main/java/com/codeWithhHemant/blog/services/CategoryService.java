package com.codeWithhHemant.blog.services;

import com.codeWithhHemant.blog.paylods.CategoryDto;
import com.codeWithhHemant.blog.paylods.CategoryResponse;

public interface CategoryService {
  CategoryDto createCategory(CategoryDto categoryDto);
  CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
  CategoryDto getCategoryById(Integer catId);
  CategoryDto updateCategory(Integer catId,CategoryDto categoryDto);
  void deleteCategory(Integer catId);
}
