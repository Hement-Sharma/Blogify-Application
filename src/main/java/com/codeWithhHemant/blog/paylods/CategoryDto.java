package com.codeWithhHemant.blog.paylods;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.internal.build.AllowNonPortable;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {
    private Integer categoryId;

    @NotBlank(message = "categoryTitle Cannot Be Empty")
    @Size(max = 15,message = "CategoryTitle Must not have Greater Than 15 char's... ")
    private String categoryTitle;

    @NotBlank
    @Size(min = 5,message = "CategoryDescription Must have atleast 5 and above Char's... ")
    private String categoryDescription;
}
