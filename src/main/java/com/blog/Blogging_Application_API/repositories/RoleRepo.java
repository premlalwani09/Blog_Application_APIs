package com.blog.Blogging_Application_API.repositories;

import com.blog.Blogging_Application_API.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Integer> {

}
