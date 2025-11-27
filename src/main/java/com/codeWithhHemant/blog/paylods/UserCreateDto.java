package com.codeWithhHemant.blog.paylods;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserCreateDto {

    @NotEmpty(message = "name of the user cannot be empty...")//NotNull+NotEmpty -->validation
    @Size(min = 3,message = "User Name Must Be Greater Than 3 Chars...")
    private String name;

    @Email(message = "Email Address Is Not Valid...")
    private String email;

    @NotEmpty(message = "password field cannot be empty...")
    @Size(min=5,max=15,message = "The Password Size Must From 5 To 15 Chars...")
    private String password;

    @NotEmpty(message = "about section of user cannot be empty...")
    private String about;

    @NotEmpty(message = "role ids cannot be empty...")
    List<Integer> roleIds;

}
