package com.codeWithhHemant.blog.services;

import com.codeWithhHemant.blog.paylods.CategoryDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {
  CategoryDto createCategory(CategoryDto categoryDto);
  List<CategoryDto> getAllCategories();
  CategoryDto getCategoryById(Integer catId);
  CategoryDto updateCategory(Integer catId,CategoryDto categoryDto);
  void deleteCategory(Integer catId);
}
