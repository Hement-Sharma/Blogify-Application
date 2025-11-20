package com.codeWithhHemant.blog.controllers;

import com.codeWithhHemant.blog.entities.Post;
import com.codeWithhHemant.blog.paylods.ApiResponse;
import com.codeWithhHemant.blog.paylods.PostDto;
import com.codeWithhHemant.blog.paylods.PostResponse;
import com.codeWithhHemant.blog.services.FileService;
import com.codeWithhHemant.blog.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("api")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    FileService fileService;

    @Value("${project.images}")
    String path;

    //add Post
    @PostMapping("category/{catId}/user/{userId}/post")
    public ResponseEntity<PostDto> addPost(
                                           @Valid @RequestBody PostDto postDto,
                                            @PathVariable Integer catId,
                                            @PathVariable  Integer userId)
    {

          PostDto createdPost = postService.createPost(postDto,catId,userId);
          return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    //Get All Posts
    @GetMapping("posts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNo",defaultValue = "0",required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = "postId",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDirection)
    {
        PostResponse postResponse = postService.getAllPosts(pageNumber,pageSize,sortBy,sortDirection);
        return ResponseEntity.ok(postResponse);
    }

    //get Single Post By Id
    @GetMapping("post/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
        PostDto postDto = postService.getPostById(postId);
        return new ResponseEntity<>(postDto,HttpStatus.OK);
    }

    //update post
    @PutMapping("post/{postId}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto,@PathVariable Integer postId){
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
    public ResponseEntity<PostResponse> getAllPostByUser(
            @PathVariable Integer userId,
            @RequestParam(value = "pageNo",defaultValue = "0",required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = "postId",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDirection)
    {
        PostResponse postResponse = postService.getPostsByUser(userId,pageNumber,pageSize,sortBy,sortDirection);
        return ResponseEntity.ok(postResponse);
    }

    //get all posts based on category
    @GetMapping("category/{catId}/posts")
    public ResponseEntity<PostResponse> getAllPostByCategory(
               @PathVariable Integer catId,
               @RequestParam(value = "pageNo",defaultValue = "0",required = false) Integer pageNumber,
               @RequestParam(value = "pageSize",defaultValue = "5",required = false) Integer pageSize,
               @RequestParam(value = "sortBy",defaultValue = "postId",required = false) String sortBy,
               @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDirection)
    {
        PostResponse postResponse = postService.getPostsByCategory(catId,pageNumber,pageSize,sortBy,sortDirection);
        return ResponseEntity.ok(postResponse);
    }

    //search post by a keyword
    @GetMapping("keyword/{keyWord}/posts")
    public ResponseEntity<PostResponse> getPostsByKeyword(
            @PathVariable String keyWord,
            @RequestParam(value = "pageNo",defaultValue = "0",required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = "postId",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDirection)
    {
        PostResponse postResponse = postService.getPostsByKeyword(keyWord,pageNumber,pageSize,sortBy,sortDirection);
        return ResponseEntity.ok(postResponse);
    }

    // to upload a image
    @PostMapping("post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile file,@PathVariable Integer postId) throws IOException {
        PostDto postDto = postService.getPostById(postId);

        String fileName = fileService.uploadFile(path,file); //fileName => imageName
        postDto.setImageName(fileName);
        PostDto updatedPostDto = postService.updatePost(postDto,postId);
        return new ResponseEntity<>(updatedPostDto,HttpStatus.OK);
    }

    //to serve the file/image
    @GetMapping(value="post/image/{imageName}")
        public void serveImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
        InputStream resource = fileService.getFile(path,imageName);
//        response.setContentType(MediaType.IMAGE_JPEG_VALUE);   //it will only stick to Jpeg imges but images can be of any types
                                                                 //so setting contentType dynemically.
        // Detect the file type automatically (jpeg, png, gif, etc.)
        String contentType = Files.probeContentType(Paths.get(path, imageName));
        response.setContentType(contentType);
        StreamUtils.copy(resource,response.getOutputStream());
    }

}
