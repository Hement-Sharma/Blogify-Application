package com.codeWithhHemant.blog.paylods;

import com.codeWithhHemant.blog.entities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserResponseDto {

    private Integer id;
    private String name;
    private String email;
    private String about;
    List<Role> roles;

}
