package com.codeWithhHemant.blog.services;

import com.codeWithhHemant.blog.paylods.UserCreateDto;
import com.codeWithhHemant.blog.paylods.UserResponse;
import com.codeWithhHemant.blog.paylods.UserResponseDto;
import com.codeWithhHemant.blog.paylods.UserUpdateDto;

public interface UserService {
     UserResponseDto createUser(UserCreateDto userDto);
     UserResponseDto updateUser(UserUpdateDto userUpdateDto, Integer id);
     UserResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
     UserResponseDto getUserById(Integer id);
     void delteUser(Integer id);
}
