package com.codeWithhHemant.blog.paylods;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class CommentDto{
    private Integer id;

    @NotBlank(message = "content of comment Cannot Be Empty")
    @Size(max=250,message = "the size of comment content must be 250 or less...")
    private String content;

    private UserDto user;
    private PostDto post;
}

