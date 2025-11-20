package com.codeWithhHemant.blog.repositories;

import com.codeWithhHemant.blog.entities.Category;
import com.codeWithhHemant.blog.entities.Post;
import com.codeWithhHemant.blog.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);
    Page<Post> findByCategory(Category category, Pageable pageable);
    Page<Post> findByUser(User category, Pageable pageable);

    Page<Post> findByTitleContaining(String keyword,Pageable pageable);
}
