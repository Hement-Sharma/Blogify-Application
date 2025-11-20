package com.codeWithhHemant.blog.paylods;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@NoArgsConstructor
@Getter
@Setter
public class PostDto {

    private Integer PostId;

    @NotEmpty(message="title cannot be empty or null")
    @Size(min = 4,max = 50,message = "title must be enough, short and sweet b/w 4 to 50 char's")
    private String title;

    @NotEmpty(message = "content cannot be empty !!! ")
    @Size(max = 500 ,message = "content length should not be greater than 500")
    private String content;


    private String imageName;
    private Date  addedDate;
    private CategoryDto category;
    private UserDto user;
}
