package com.blog.Blogging_Application_API.security;

import com.blog.Blogging_Application_API.entities.User;
import com.blog.Blogging_Application_API.exceptions.ResourceNotfoundException;
import com.blog.Blogging_Application_API.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Loading User from Database by Username
        User user = this.userRepo.findByEmail(username).orElseThrow(() -> new ResourceNotfoundException("User", "email : "+username, 0));

        return user;
    }
}
