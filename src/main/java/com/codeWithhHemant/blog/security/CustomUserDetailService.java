package com.codeWithhHemant.blog.security;

import com.codeWithhHemant.blog.entities.User;
import com.codeWithhHemant.blog.exceptions.ResourceNotFoundException;
import com.codeWithhHemant.blog.paylods.UserPrinciple;
import com.codeWithhHemant.blog.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("User","Email",username));
        return new UserPrinciple(user);
    }
}
