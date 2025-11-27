package com.codeWithhHemant.blog.controllers;

import com.codeWithhHemant.blog.entities.Role;
import com.codeWithhHemant.blog.paylods.ApiResponse;
import com.codeWithhHemant.blog.services.RoleService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class RoleController {

    @Autowired
    RoleService roleService;

    @PostMapping("role")
    @PreAuthorize("hasRole('ADMIN')")  //only admin can access this
    public ResponseEntity<Role> addRole(@RequestBody Role role){
       Role savedRole = roleService.addRole(role);
       return new ResponseEntity<>(savedRole, HttpStatus.CREATED);
    }

    @GetMapping("roles")
    @PreAuthorize("hasRole('ADMIN')")  //only admin can access this
    public ResponseEntity<List<Role>> getAllRoles(){
        List<Role> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @PutMapping("role/{roleId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Role> updateRole(@RequestBody Role role, @PathVariable Integer roleId){
        Role updatedRole = roleService.updateRole(role,roleId);
        return ResponseEntity.ok(updatedRole);
    }

    @DeleteMapping("role/{roleId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteRole(@PathVariable Integer roleId){
        roleService.deleteRole(roleId);
        return new ResponseEntity<>(new ApiResponse("The Role is deleted Successfully...",true),HttpStatus.OK);
    }
}
