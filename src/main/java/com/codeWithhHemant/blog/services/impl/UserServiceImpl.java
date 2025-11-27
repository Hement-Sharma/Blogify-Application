package com.codeWithhHemant.blog.services.impl;

import com.codeWithhHemant.blog.entities.Role;
import com.codeWithhHemant.blog.entities.User;
import com.codeWithhHemant.blog.exceptions.ResourceNotFoundException;
import com.codeWithhHemant.blog.paylods.UserCreateDto;
import com.codeWithhHemant.blog.paylods.UserResponse;
import com.codeWithhHemant.blog.paylods.UserResponseDto;
import com.codeWithhHemant.blog.paylods.UserUpdateDto;
import com.codeWithhHemant.blog.repositories.RoleRepo;
import com.codeWithhHemant.blog.repositories.UserRepo;
import com.codeWithhHemant.blog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto createUser(UserCreateDto userCreateDto) {
        List<Role> roles = userCreateDto
                                        .getRoleIds()
                                        .stream()
                                        .map(roleId -> roleRepo.findById(roleId).orElseThrow(()->new ResourceNotFoundException("Role","Id",roleId)))
                                        .collect(Collectors.toList());

        User user = modelMapper.map(userCreateDto,User.class);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword())); //Encoding the coming passwords
        User savedUser = userRepo.save(user);
        return modelMapper.map(savedUser, UserResponseDto.class);
    }

    @Override
    public UserResponseDto updateUser(UserUpdateDto userUpdateDto, Integer id) {
        User user = userRepo.findById(id)
                                     .orElseThrow(()->new ResourceNotFoundException("USER ","ID",id));

        user.setName(userUpdateDto.getName());
        user.setEmail(userUpdateDto.getEmail());
        user.setPassword(userUpdateDto.getPassword());
        user.setAbout(userUpdateDto.getAbout());

        List<Integer> roleIds = userUpdateDto.getRoleIds();

        if(roleIds != null){//if user is sending role id's for update then update other wise not.

            List<Role> roles = new ArrayList<>();
            for(Integer roleId : roleIds){
                Role role = roleRepo.findById(roleId).orElseThrow(()->new ResourceNotFoundException("Role","Id",roleId));
                roles.add(role);
            }

            user.setRoles(roles);
        }

        User updatedUser = userRepo.save(user);

        return modelMapper.map(updatedUser, UserResponseDto.class);
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
        List<UserResponseDto> userResponseDtos = users.stream().map(user -> modelMapper.map(user, UserResponseDto.class)).collect(Collectors.toList());

        UserResponse userResponse = new UserResponse();
        userResponse.setUsers(userResponseDtos);
        userResponse.setPageNumber(pageUsers.getNumber());
        userResponse.setPageSize(pageUsers.getSize());
        userResponse.setTotalPages(pageUsers.getTotalPages());
        userResponse.setTotalElements(pageUsers.getTotalElements());
        userResponse.setLastPage(pageUsers.isLast());

        return userResponse;
    }

    @Override
    public UserResponseDto getUserById(Integer id) {
        User user = userRepo.findById(id)
                                        .orElseThrow(()->new ResourceNotFoundException("USER ","ID",id));
        UserResponseDto userResponseDto = modelMapper.map(user, UserResponseDto.class);
        return userResponseDto;
    }

    @Override
    public void delteUser(Integer id) {
        User user = userRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("USER","ID",id));

        userRepo.delete(user);
    }
}
