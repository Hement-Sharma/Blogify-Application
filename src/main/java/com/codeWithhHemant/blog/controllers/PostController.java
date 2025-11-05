package com.codeWithhHemant.blog.controllers;

import com.codeWithhHemant.blog.entities.Post;
import com.codeWithhHemant.blog.paylods.ApiResponse;
import com.codeWithhHemant.blog.paylods.PostDto;
import com.codeWithhHemant.blog.services.PostService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class PostController {

    @Autowired
    PostService postService;

    //add Post
    @PostMapping("category/{catId}/user/{userId}/post")
    public ResponseEntity<PostDto> addPost(
                                            @RequestBody PostDto postDto,
                                            @PathVariable Integer catId,
                                            @PathVariable  Integer userId){

          PostDto createdPost = postService.createPost(postDto,catId,userId);
          return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    //Get All Posts
    @GetMapping("posts")
    public ResponseEntity<List<PostDto>> getAllPosts(){
        List<PostDto> postDtos = postService.getAllPosts();
        return new ResponseEntity<>(postDtos,HttpStatus.OK);
    }

    //get Single Post By Id
    @GetMapping("post/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
        PostDto postDto = postService.getPostById(postId);
        return new ResponseEntity<>(postDto,HttpStatus.OK);
    }

    //update post
    @PutMapping("post/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId){
        PostDto updatedPostDto = postService.updatePost(postDto,postId);
        return new ResponseEntity<>(updatedPostDto, HttpStatus.OK);
    }

    //delete post
    @DeleteMapping("post/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
        postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse("The Post Is Deleted SuccessFully",true),HttpStatus.OK);
    }

    //get all posts based on user
    @GetMapping("user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getAllPostByUser(@PathVariable Integer userId){
        List<PostDto> postDtos = postService.getPostsByUser(userId);
        return ResponseEntity.ok(postDtos);
    }

    //get all posts based on category
    @GetMapping("category/{catId}/posts")
    public ResponseEntity<List<PostDto>> getAllPostByCategory(@PathVariable Integer catId){
        List<PostDto> postDtos = postService.getPostsByCategory(catId);
        return ResponseEntity.ok(postDtos);
    }
}
