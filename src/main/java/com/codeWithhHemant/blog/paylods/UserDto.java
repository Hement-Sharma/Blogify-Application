package com.codeWithhHemant.blog.paylods;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

    private Integer id;

    @NotEmpty//NotNull+NotEmpty -->validation
    @Size(min = 3,message = "User Name Must Be Greater Than 3 Chars...")
    private String name;

    @Email(message = "Email Address Is Not Valid...")
    private String email;

    @NotEmpty
    @Size(min=5,max=15,message = "The Password Size Must From 5 To 15 Chars...")
    private String password;

    @NotEmpty
    private String about;

}
