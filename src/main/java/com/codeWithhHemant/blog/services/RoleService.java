package com.codeWithhHemant.blog.services;

import com.codeWithhHemant.blog.entities.Role;

import java.util.List;

public interface RoleService{
    Role addRole(Role role);
    List<Role> getAllRoles();
    Role updateRole(Role role,Integer roleId);
    void deleteRole(Integer roleId);
}
