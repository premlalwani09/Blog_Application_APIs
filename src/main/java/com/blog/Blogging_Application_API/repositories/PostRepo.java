package com.blog.Blogging_Application_API.repositories;

import com.blog.Blogging_Application_API.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer> {

    Page<Post> findByUserId(Integer id, Pageable p);

    Page<Post> findByCategoryCategoryId(Integer categoryId, Pageable p);

    Page<Post> findByPostIdIn(List<Integer> postIds, Pageable p);

    List<Post> findByTitleContaining(String title);
}
