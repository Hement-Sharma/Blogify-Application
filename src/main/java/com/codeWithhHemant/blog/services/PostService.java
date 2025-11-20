package com.codeWithhHemant.blog.services;

import com.codeWithhHemant.blog.paylods.CategoryDto;
import com.codeWithhHemant.blog.paylods.PostDto;
import com.codeWithhHemant.blog.paylods.PostResponse;

import java.util.List;

public interface PostService {

   //create Post
    PostDto createPost(PostDto postDto,Integer categoryId,Integer userId);

    //get all posts
    PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);

    //get single post
    PostDto getPostById(Integer postId);

    //update Post
    PostDto updatePost(PostDto postDto,Integer postId);

    //delete Post
    void deletePost(Integer postId);

    //get Posts based on user
    PostResponse getPostsByUser(Integer userId,Integer pageNumber,Integer pageSize,String sortBy,String sortDir);

    //get Posts Based On Category
    PostResponse getPostsByCategory(Integer categoryId,Integer pageNumber,Integer pageSize,String sortBy,String sortDir);

 PostResponse getPostsByKeyword(String keyword,Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
}
