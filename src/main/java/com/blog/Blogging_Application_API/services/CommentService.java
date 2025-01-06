package com.blog.Blogging_Application_API.services;

import com.blog.Blogging_Application_API.payloads.CommentDTO;

public interface CommentService {

    public CommentDTO createComment(CommentDTO commentDTO, Integer postId, Integer userId);

    public void deleteComment(Integer commentId);
}
