package com.codeWithhHemant.blog.paylods;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class PostDto {
    private Integer PostId;
    private String title;
    private String content;
    private String imageName;
    private Date  addedDate;
    private CategoryDto category;
    private UserDto user;
}
