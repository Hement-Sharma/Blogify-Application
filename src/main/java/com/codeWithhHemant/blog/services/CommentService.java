package com.codeWithhHemant.blog.services;

import com.codeWithhHemant.blog.paylods.CommentDto;
import com.codeWithhHemant.blog.paylods.CommentResponse;
import com.codeWithhHemant.blog.paylods.CommentDto;
import com.codeWithhHemant.blog.paylods.CommentResponse;

public interface CommentService {
    //add comment
    CommentDto createComment(CommentDto commentDto,Integer userId,Integer postId);

    //get all comments
    CommentResponse getAllComments(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    //get single comment
    CommentDto getCommentById(Integer commentId);

    //update comment
    CommentDto updateComment(CommentDto CommentDto,Integer CommentId);

    //delete Comment
    void deleteComment(Integer CommentId);

    //get Comments based on user
    CommentResponse getCommentsByUser(Integer userId,Integer pageNumber,Integer pageSize,String sortBy,String sortDir);

    //get Comments based on Post
    CommentResponse getCommentsByPost(Integer postId,Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
}
