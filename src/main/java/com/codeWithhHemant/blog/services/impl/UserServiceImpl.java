package com.codeWithhHemant.blog.services.impl;

import com.codeWithhHemant.blog.entities.Post;
import com.codeWithhHemant.blog.entities.User;
import com.codeWithhHemant.blog.exceptions.ResourceNotFoundException;
import com.codeWithhHemant.blog.paylods.PostDto;
import com.codeWithhHemant.blog.paylods.PostResponse;
import com.codeWithhHemant.blog.paylods.UserDto;
import com.codeWithhHemant.blog.paylods.UserResponse;
import com.codeWithhHemant.blog.repositories.UserRepo;
import com.codeWithhHemant.blog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public UserResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort;

        if(sortDir.equalsIgnoreCase("desc")){ //descending
            sort = Sort.by(sortBy).descending();
        }else{
            sort = Sort.by(sortBy);  //by default ascending.
        }

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<User> pageUsers = userRepo.findAll(pageable);
        List<User> users = pageUsers.toList();
        List<UserDto> userDtos = users.stream().map(user -> modelMapper.map(user,UserDto.class)).collect(Collectors.toList());

        UserResponse userResponse = new UserResponse();
        userResponse.setUsers(userDtos);
        userResponse.setPageNumber(pageUsers.getNumber());
        userResponse.setPageSize(pageUsers.getSize());
        userResponse.setTotalPages(pageUsers.getTotalPages());
        userResponse.setTotalElements(pageUsers.getTotalElements());
        userResponse.setLastPage(pageUsers.isLast());

        return userResponse;
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
