package com.codeWithhHemant.blog.paylods;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserLoginDetails{

    @Email(message = "Email Address Is Not Valid...")
    private String userName;    //in our case email.

    @NotEmpty(message = "password field cannot be empty...")
    private String password;
}
