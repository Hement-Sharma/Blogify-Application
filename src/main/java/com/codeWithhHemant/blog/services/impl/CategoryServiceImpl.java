package com.codeWithhHemant.blog.services.impl;

import com.codeWithhHemant.blog.entities.Category;
import com.codeWithhHemant.blog.exceptions.ResourceNotFoundException;
import com.codeWithhHemant.blog.paylods.CategoryDto;
import com.codeWithhHemant.blog.paylods.CategoryResponse;
import com.codeWithhHemant.blog.repositories.CategoryRepo;
import com.codeWithhHemant.blog.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    ModelMapper mapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category savedCategory = categoryRepo.save(this.mapper.map(categoryDto, Category.class));
        return this.mapper.map(savedCategory,CategoryDto.class);
    }

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort;

        if(sortDir.equalsIgnoreCase("desc")){ //id descending
            sort = Sort.by(sortBy).descending();
        }else{
            sort = Sort.by(sortBy);  //by default ascending.
        }

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<Category> pageCategories = categoryRepo.findAll(pageable);
        List<Category> categories = pageCategories.toList();
        List<CategoryDto> categoryDtos = categories.stream().map(category -> mapper.map(category,CategoryDto.class)).collect(Collectors.toList());

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setCategories(categoryDtos);
        categoryResponse.setPageNumber(pageCategories.getNumber());
        categoryResponse.setPageSize(pageCategories.getSize());
        categoryResponse.setTotalPages(pageCategories.getTotalPages());
        categoryResponse.setTotalElements(pageCategories.getTotalElements());
        categoryResponse.setLastPage(pageCategories.isLast());

        return categoryResponse;
    }

    @Override
    public CategoryDto getCategoryById(Integer catId) {
        Category category = this.categoryRepo.findById(catId).orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",catId));
        return this.mapper.map(category,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(Integer catId, CategoryDto categoryDto) {
        Category category = this.categoryRepo.findById(catId).orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",catId));

        //updating
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCategory = this.categoryRepo.save(category);

        return this.mapper.map(updatedCategory,CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer catId) {
      Category category = this.categoryRepo.findById(catId).orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",catId));
      this.categoryRepo.delete(category);
    }
}
