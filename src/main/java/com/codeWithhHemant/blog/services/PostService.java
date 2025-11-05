package com.codeWithhHemant.blog.services;

import com.codeWithhHemant.blog.paylods.CategoryDto;
import com.codeWithhHemant.blog.paylods.PostDto;

import java.util.List;

public interface PostService {

   //create Post
    PostDto createPost(PostDto postDto,Integer categoryId,Integer userId);

    //get all posts
    List<PostDto> getAllPosts();

    //get single post
    PostDto getPostById(Integer postId);

    //update Post
    PostDto updatePost(PostDto postDto,Integer postId);

    //delete Post
    void deletePost(Integer postId);

    //get Posts based on user
    List<PostDto> getPostsByUser(Integer userId);

    //get Posts Based On Category
    List<PostDto> getPostsByCategory(Integer categoryId);
}
