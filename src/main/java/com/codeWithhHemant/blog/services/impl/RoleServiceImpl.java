package com.codeWithhHemant.blog.services.impl;

import com.codeWithhHemant.blog.entities.Role;
import com.codeWithhHemant.blog.exceptions.ResourceNotFoundException;
import com.codeWithhHemant.blog.repositories.RoleRepo;
import com.codeWithhHemant.blog.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepo roleRepo;

    @Override
    public Role addRole(Role role) {
        Role savedRole = roleRepo.save(role);
        return savedRole;
    }

    @Override
    public List<Role> getAllRoles() {
        List<Role> roles = roleRepo.findAll();
        return roles;
    }

    @Override
    public Role updateRole(Role role,Integer roleId) {
        Role roleToUpdate = roleRepo.findById(roleId).orElseThrow(()-> new ResourceNotFoundException("Role","Id",roleId));
        roleToUpdate.setRole(role.getRole());

        Role updatedRole = roleRepo.save(roleToUpdate);

        return updatedRole;
    }

    @Override
    public void deleteRole(Integer roleId) {
        Role roleToDelete = roleRepo.findById(roleId).orElseThrow(()-> new ResourceNotFoundException("Role","Id",roleId));
        roleRepo.delete(roleToDelete);
    }
}
