package com.codeWithhHemant.blog.controllers;

import com.codeWithhHemant.blog.paylods.*;
import com.codeWithhHemant.blog.services.CommentService;
import com.codeWithhHemant.blog.services.FileService;
import com.codeWithhHemant.blog.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("api")
public class CommentController{

    @Autowired
    CommentService commentService;

    //add Comment
    @PostMapping("post/{postId}/user/{userId}/comment")
    public ResponseEntity<CommentDto> addComment(
           @Valid @RequestBody CommentDto commentDto,
                  @PathVariable Integer postId,
                  @PathVariable  Integer userId)
    {

        CommentDto createdCommentDto = commentService.createComment(commentDto,userId,postId);
        return new ResponseEntity<>(createdCommentDto, HttpStatus.CREATED);
    }

    //Get All Comments
    @GetMapping("comments")
    public ResponseEntity<CommentResponse> getAllPosts(
            @RequestParam(value = "pageNo",defaultValue = "0",required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = "id",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDirection)
    {
        CommentResponse commentResponse = commentService.getAllComments(pageNumber,pageSize,sortBy,sortDirection);
        return ResponseEntity.ok(commentResponse);
    }

    //get Single Comment By Id
    @GetMapping("comment/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Integer commentId){
        CommentDto commentDto = commentService.getCommentById(commentId);
        return new ResponseEntity<>(commentDto,HttpStatus.OK);
    }

    //update Comment
    @PutMapping("comment/{commentId}")
    public ResponseEntity<CommentDto> updatePost(@Valid @RequestBody CommentDto commentDto,@PathVariable Integer commentId){
        CommentDto updatedCommentDto = commentService.updateComment(commentDto,commentId);
        return new ResponseEntity<>(updatedCommentDto, HttpStatus.OK);
    }

    //delete post
    @DeleteMapping("comment/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("The Comment Is Deleted SuccessFully",true),HttpStatus.OK);
    }

//   get all Comments Based On user
    @GetMapping("user/{userId}/comments")
    public ResponseEntity<CommentResponse> getAllPostByUser(
            @PathVariable Integer userId,
            @RequestParam(value = "pageNo",defaultValue = "0",required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = "id",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDirection)
    {
        CommentResponse commentResponse = commentService.getCommentsByUser(userId,pageNumber,pageSize,sortBy,sortDirection);
        return ResponseEntity.ok(commentResponse);
    }

    //   get all Comments Based On post
    @GetMapping("post/{postId}/comments")
    public ResponseEntity<CommentResponse> getAllCommentsByPost(
            @PathVariable Integer postId,
            @RequestParam(value = "pageNo",defaultValue = "0",required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = "id",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDirection)
    {
        CommentResponse commentResponse = commentService.getCommentsByPost(postId,pageNumber,pageSize,sortBy,sortDirection);
        return ResponseEntity.ok(commentResponse);
    }

}
