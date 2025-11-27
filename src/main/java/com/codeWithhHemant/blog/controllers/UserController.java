package com.codeWithhHemant.blog.controllers;

import com.codeWithhHemant.blog.paylods.*;
import com.codeWithhHemant.blog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class UserController {

    @Autowired
    private UserService userService;

    //register/add user
    @PostMapping("register")
    public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody UserCreateDto userCreateDto){
         UserResponseDto savedUserResponseDto = userService.createUser(userCreateDto);
         return new ResponseEntity<>(savedUserResponseDto, HttpStatus.CREATED);
    }

    //get all users
    @GetMapping("users")
    public ResponseEntity<UserResponse> getAllUsers(
            @RequestParam(value = "pageNo",defaultValue = "0",required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = "id",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDirection)
    {
       UserResponse userResponse = userService.getAllUsers(pageNumber,pageSize,sortBy,sortDirection);
       return ResponseEntity.ok(userResponse);
    }

    //get user by id
    @GetMapping("user/{userId}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Integer userId){
       UserResponseDto userResponseDto = userService.getUserById(userId);
       return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    //update user
    @PutMapping("user/{userId}")
    public ResponseEntity<UserResponseDto> updateUser(@Valid @RequestBody UserUpdateDto userUpdateDto, @PathVariable Integer userId){
       UserResponseDto updatedUserResponseDto = userService.updateUser(userUpdateDto,userId);
       return ResponseEntity.ok(updatedUserResponseDto);
    }

    //delete user
    @PreAuthorize("hasRole('ADMIN')")  //only admin can access this
    @DeleteMapping("user/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId){
        userService.delteUser(userId);
        return new ResponseEntity<>(new ApiResponse("User Delted Successfully",true),HttpStatus.OK);
    }
}
