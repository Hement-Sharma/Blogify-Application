package com.codeWithhHemant.blog.services;

import com.codeWithhHemant.blog.paylods.UserDto;
import com.codeWithhHemant.blog.paylods.UserResponse;

import java.util.List;

public interface UserService {
     UserDto createUser(UserDto userDto);
     UserDto updateUser(UserDto userDto,Integer id);
     UserResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
     UserDto getUserById(Integer id);
     void delteUser(Integer id);
}
