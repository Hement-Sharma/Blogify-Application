package com.codeWithhHemant.blog.services.impl;

import com.codeWithhHemant.blog.entities.Category;
import com.codeWithhHemant.blog.exceptions.ResourceNotFoundException;
import com.codeWithhHemant.blog.paylods.CategoryDto;
import com.codeWithhHemant.blog.repositories.CategoryRepo;
import com.codeWithhHemant.blog.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepo repo;

    @Autowired
    ModelMapper mapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category savedCategory = repo.save(this.mapper.map(categoryDto, Category.class));
        return this.mapper.map(savedCategory,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = this.repo.findAll();
        List<CategoryDto> categoryDtos = categories.stream().map(category -> this.mapper.map(category,CategoryDto.class)).collect(Collectors.toList());
        return categoryDtos;
    }

    @Override
    public CategoryDto getCategoryById(Integer catId) {
        Category category = this.repo.findById(catId).orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",catId));
        return this.mapper.map(category,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(Integer catId, CategoryDto categoryDto) {
        Category category = this.repo.findById(catId).orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",catId));

        //updating
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCategory = this.repo.save(category);

        return this.mapper.map(updatedCategory,CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer catId) {
      Category category = this.repo.findById(catId).orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",catId));
      this.repo.delete(category);
    }
}
