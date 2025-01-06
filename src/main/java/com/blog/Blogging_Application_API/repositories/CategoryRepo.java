package com.blog.Blogging_Application_API.repositories;

import com.blog.Blogging_Application_API.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
