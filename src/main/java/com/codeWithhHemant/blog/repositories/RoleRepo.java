package com.codeWithhHemant.blog.repositories;

import com.codeWithhHemant.blog.entities.Role;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Integer>{
}
