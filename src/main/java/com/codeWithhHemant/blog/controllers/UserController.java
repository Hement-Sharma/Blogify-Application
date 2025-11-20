package com.codeWithhHemant.blog.controllers;

import com.codeWithhHemant.blog.paylods.ApiResponse;
import com.codeWithhHemant.blog.paylods.UserDto;
import com.codeWithhHemant.blog.paylods.UserResponse;
import com.codeWithhHemant.blog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class UserController {

    @Autowired
    private UserService userService;

    //add user
    @PostMapping("user")
    public ResponseEntity<UserDto> addUser(@Valid @RequestBody UserDto userDto){
         UserDto savedUserDto = userService.createUser(userDto);
         return new ResponseEntity<>(savedUserDto, HttpStatus.CREATED);
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
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer userId){
       UserDto userDto = userService.getUserById(userId);
       return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    //update user
    @PutMapping("user/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable Integer userId){
       UserDto updatedUserDto = userService.updateUser(userDto,userId);
       return ResponseEntity.ok(updatedUserDto);
    }

    //delete user
    @DeleteMapping("user/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId){
        userService.delteUser(userId);
        return new ResponseEntity<>(new ApiResponse("User Delted Successfully",true),HttpStatus.OK);
    }
}
