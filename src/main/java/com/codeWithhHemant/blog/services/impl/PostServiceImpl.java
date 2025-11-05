package com.codeWithhHemant.blog.services.impl;

import com.codeWithhHemant.blog.entities.Category;
import com.codeWithhHemant.blog.entities.Post;
import com.codeWithhHemant.blog.entities.User;
import com.codeWithhHemant.blog.exceptions.ResourceNotFoundException;
import com.codeWithhHemant.blog.paylods.PostDto;
import com.codeWithhHemant.blog.repositories.CategoryRepo;
import com.codeWithhHemant.blog.repositories.PostRepo;
import com.codeWithhHemant.blog.repositories.UserRepo;
import com.codeWithhHemant.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.CollationElementIterator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepo postRepo;

    @Autowired
    ModelMapper mapper;

    @Autowired
    CategoryRepo catRepo;

    @Autowired
    UserRepo userRepo;

    @Override
    public PostDto createPost(PostDto postDto,Integer categoryId,Integer userId) {

        Category category = catRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",categoryId));
        User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","UserId",userId));


        Post post = this.mapper.map(postDto, Post.class);
        post.setAddedDate(new Date());
        post.setImageName("default.png");
        post.setCategory(category);
        post.setUser(user);

        Post createdPost = postRepo.save(post);
        return this.mapper.map(createdPost,PostDto.class);
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> posts = postRepo.findAll();
        List<PostDto> postDtos = posts.stream().map(post -> mapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Post Id",postId));
        PostDto postDto = mapper.map(post,PostDto.class);
        return postDto;
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Post Id",postId));

        //only updating 3 fields and other remain same.
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());

        Post updatedPost = postRepo.save(post);
        return mapper.map(updatedPost,PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("POST","ID",postId));
        postRepo.delete(post);
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("USER ","ID",userId));

        List<Post> posts = postRepo.findByUser(user);
        List<PostDto> postDtos = posts.stream().map(post -> mapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {
        Category category = catRepo.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("CATEGORY ","ID",categoryId));

        List<Post> posts = postRepo.findByCategory(category);
        List<PostDto> postDtos = posts.stream().map(post -> mapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }
}
