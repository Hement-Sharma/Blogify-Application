package com.codeWithhHemant.blog.repositories;

import com.codeWithhHemant.blog.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Integer> {
}
