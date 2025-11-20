package com.codeWithhHemant.blog.services.impl;

import com.codeWithhHemant.blog.entities.Comment;
import com.codeWithhHemant.blog.entities.Post;
import com.codeWithhHemant.blog.entities.Post;
import com.codeWithhHemant.blog.entities.User;
import com.codeWithhHemant.blog.exceptions.ResourceNotFoundException;
import com.codeWithhHemant.blog.paylods.*;
import com.codeWithhHemant.blog.paylods.PostDto;
import com.codeWithhHemant.blog.paylods.PostResponse;
import com.codeWithhHemant.blog.repositories.CommentRepo;
import com.codeWithhHemant.blog.repositories.PostRepo;
import com.codeWithhHemant.blog.repositories.PostRepo;
import com.codeWithhHemant.blog.repositories.UserRepo;
import com.codeWithhHemant.blog.services.CommentService;
import com.codeWithhHemant.blog.services.PostService;
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
public class CommentServiceImpl implements CommentService {

    @Autowired
    PostRepo postRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    CommentRepo commentRepo;

    @Autowired
    ModelMapper mapper;

    @Override
    public CommentDto createComment(CommentDto commentDto,Integer userId, Integer PostId) {
        User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","UserId",userId));
        Post Post = postRepo.findById(PostId).orElseThrow(()->new ResourceNotFoundException("Post","PostId",PostId));

        Comment comment = mapper.map(commentDto, Comment.class);
        comment.setPost(Post);
        comment.setUser(user);

        Comment savedComment = commentRepo.save(comment);
        return mapper.map(savedComment,CommentDto.class);
    }

    @Override
    public CommentResponse getAllComments(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sort;

        if(sortDir.equalsIgnoreCase("desc")){ //id descending
            sort = Sort.by(sortBy).descending();
        }else{
            sort = Sort.by(sortBy);  //by default ascending.
        }

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<Comment> pageComments = commentRepo.findAll(pageable);
        List<Comment> comments = pageComments.toList();

        List<CommentDto> commentDtos = comments.stream().map(comment -> mapper.map(comment,CommentDto.class)).collect(Collectors.toList());

        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setComments(commentDtos);
        commentResponse.setPageNumber(pageComments.getNumber());
        commentResponse.setPageSize(pageComments.getSize());
        commentResponse.setTotalPages(pageComments.getTotalPages());
        commentResponse.setTotalElements(pageComments.getTotalElements());
        commentResponse.setLastPage(pageComments.isLast());

        return commentResponse;
    }

    @Override
    public CommentDto getCommentById(Integer commentId) {
        Comment comment = commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","CommentId",commentId));
        return mapper.map(comment,CommentDto.class);
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, Integer commentId) {
        Comment comment = commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","Comment ID",commentId));

        //only updating 3 fields and other remain same.
        comment.setContent(commentDto.getContent());

        Comment updatedComment = commentRepo.save(comment);
        return mapper.map(updatedComment,CommentDto.class);

    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","Comment ID",commentId));
        commentRepo.delete(comment);
    }

    @Override
    public CommentResponse getCommentsByUser(Integer userId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort;

        if(sortDir.equalsIgnoreCase("desc")){ //id descending
            sort = Sort.by(sortBy).descending();
        }else{
            sort = Sort.by(sortBy);  //by default ascending.
        }

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","UserId",userId));

        Page<Comment> pageComments = commentRepo.findByUser(user,pageable);
        List<Comment> comments = pageComments.toList();
        List<CommentDto> commentDtos = comments.stream().map(comment -> mapper.map(comment,CommentDto.class)).collect(Collectors.toList());

        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setComments(commentDtos);
        commentResponse.setPageNumber(pageComments.getNumber());
        commentResponse.setPageSize(pageComments.getSize());
        commentResponse.setTotalPages(pageComments.getTotalPages());
        commentResponse.setTotalElements(pageComments.getTotalElements());
        commentResponse.setLastPage(pageComments.isLast());
         return commentResponse;
    }

    @Override
    public CommentResponse getCommentsByPost(Integer postId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort;

        if(sortDir.equalsIgnoreCase("desc")){ //id descending
            sort = Sort.by(sortBy).descending();
        }else{
            sort = Sort.by(sortBy);  //by default ascending.
        }

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","PostId",postId));

        Page<Comment> pageComments = commentRepo.findByPost(post,pageable);
        List<Comment> comments = pageComments.toList();
        List<CommentDto> commentDtos = comments.stream().map(comment -> mapper.map(comment,CommentDto.class)).collect(Collectors.toList());

        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setComments(commentDtos);
        commentResponse.setPageNumber(pageComments.getNumber());
        commentResponse.setPageSize(pageComments.getSize());
        commentResponse.setTotalPages(pageComments.getTotalPages());
        commentResponse.setTotalElements(pageComments.getTotalElements());
        commentResponse.setLastPage(pageComments.isLast());
        return commentResponse;
    }


}
