package com.codeWithhHemant.blog.controllers;

import com.codeWithhHemant.blog.paylods.ApiResponse;
import com.codeWithhHemant.blog.paylods.CategoryDto;
import com.codeWithhHemant.blog.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@RestController()
@RequestMapping("api")
public class CategoryController{

    @Autowired
    CategoryService service;

    @PostMapping("category")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto createdCategoryDto = this.service.createCategory(categoryDto);
        return new ResponseEntity<>(createdCategoryDto, HttpStatus.CREATED);
    }

    @GetMapping("categories")
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        List<CategoryDto> categoryDtos = this.service.getAllCategories();
        return ResponseEntity.ok(categoryDtos);
    }

    @GetMapping("category/{catId}")
    public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable Integer catId){
        CategoryDto categoryDto = this.service.getCategoryById(catId);
        return ResponseEntity.ok(categoryDto);
    }

    @PutMapping("category/{catId}")
     public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto ,@PathVariable Integer catId){
       CategoryDto updatedCategoryDto = this.service.updateCategory(catId,categoryDto);
       return new ResponseEntity<>(updatedCategoryDto,HttpStatus.OK);
    }

    @DeleteMapping("category/{catId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId){
       this.service.deleteCategory(catId);
       return new ResponseEntity<>(new ApiResponse("Category Deleted Successfully",true),HttpStatus.OK);
    }
}
