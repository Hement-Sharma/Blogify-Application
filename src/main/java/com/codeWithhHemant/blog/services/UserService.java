package com.codeWithhHemant.blog.services;

import com.codeWithhHemant.blog.entities.User;
import com.codeWithhHemant.blog.paylods.UserDto;

import java.util.List;

public interface UserService {
    public UserDto createUser(UserDto userDto);
    public UserDto updateUser(UserDto userDto,Integer id);
    public List<UserDto> getAllUsers();
    public UserDto getUserById(Integer id);
    public void delteUser(Integer id);
}
