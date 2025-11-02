package com.codeWithhHemant.blog.services.impl;

import com.codeWithhHemant.blog.entities.User;
import com.codeWithhHemant.blog.exceptions.ResourceNotFoundException;
import com.codeWithhHemant.blog.paylods.UserDto;
import com.codeWithhHemant.blog.repositories.UserRepo;
import com.codeWithhHemant.blog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.userDtoToUser(userDto);
        User savedUser = userRepo.save(user);
        return this.userToUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer id) {
        User user = userRepo.findById(id)
                                     .orElseThrow(()->new ResourceNotFoundException("USER ","ID",id));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        User updatedUser = userRepo.save(user);

        return this.userToUserDto(updatedUser);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepo.findAll();
        List<UserDto> userDtos = users.stream().map(user->this.userToUserDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public UserDto getUserById(Integer id) {
        User user = userRepo.findById(id)
                                        .orElseThrow(()->new ResourceNotFoundException("USER ","ID",id));
        UserDto userDto = this.userToUserDto(user);
        return userDto;
    }

    @Override
    public void delteUser(Integer id) {
        User user = userRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("USER","ID",id));

        userRepo.delete(user);
    }


    public UserDto userToUserDto(User user) {
        UserDto userDto = modelMapper.map(user,UserDto.class);
        return userDto;
    }


    public User userDtoToUser(UserDto userDto) {
        User user = modelMapper.map(userDto,User.class);
        return user;
    }
}
