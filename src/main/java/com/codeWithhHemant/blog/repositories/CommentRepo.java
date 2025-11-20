package com.codeWithhHemant.blog.repositories;

import com.codeWithhHemant.blog.entities.Comment;
import com.codeWithhHemant.blog.entities.Post;
import com.codeWithhHemant.blog.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment,Integer> {
     Page<Comment> findByUser(User user, Pageable pageable);
     Page<Comment> findByPost(Post post, Pageable pageable);
}
