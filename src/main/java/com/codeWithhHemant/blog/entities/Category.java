package com.codeWithhHemant.blog.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "categories")
@NoArgsConstructor
@Getter
@Setter
public class Category {

    @Id
    @Column(name = "catId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    @Column(name = "catTitle",nullable = false,length = 50)
    private String categoryTitle;

    @Column(name = "catDescription",nullable = false)
    private String categoryDescription;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Post> posts;
}
