package com.blog.Blogging_Application_API.repositories;

import com.blog.Blogging_Application_API.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
}
