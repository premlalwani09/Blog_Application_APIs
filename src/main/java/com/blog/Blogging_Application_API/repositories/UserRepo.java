package com.blog.Blogging_Application_API.repositories;

import com.blog.Blogging_Application_API.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {


    Optional<User> findByEmail(String email);
}
