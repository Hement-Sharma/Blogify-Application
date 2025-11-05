package com.codeWithhHemant.blog.services;

import com.codeWithhHemant.blog.entities.User;
import com.codeWithhHemant.blog.paylods.UserDto;

import java.util.List;

public interface UserService {
     UserDto createUser(UserDto userDto);
     UserDto updateUser(UserDto userDto,Integer id);
     List<UserDto> getAllUsers();
     UserDto getUserById(Integer id);
     void delteUser(Integer id);
}
