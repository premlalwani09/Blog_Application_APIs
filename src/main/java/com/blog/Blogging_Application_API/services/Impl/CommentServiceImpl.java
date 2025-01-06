package com.blog.Blogging_Application_API.services.Impl;

import com.blog.Blogging_Application_API.entities.Comment;
import com.blog.Blogging_Application_API.entities.Post;
import com.blog.Blogging_Application_API.entities.User;
import com.blog.Blogging_Application_API.exceptions.ResourceNotfoundException;
import com.blog.Blogging_Application_API.payloads.CommentDTO;
import com.blog.Blogging_Application_API.repositories.CommentRepo;
import com.blog.Blogging_Application_API.repositories.PostRepo;
import com.blog.Blogging_Application_API.repositories.UserRepo;
import com.blog.Blogging_Application_API.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;




    @Override
    public CommentDTO createComment(CommentDTO commentDTO, Integer postId, Integer userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotfoundException("User", "id", userId));

        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotfoundException("Post", "postId", postId));

        Comment comment = this.modelMapper.map(commentDTO, Comment.class);

        comment.setPost(post);
        comment.setUser(user);

        Comment savedComment = this.commentRepo.save(comment);

        return this.modelMapper.map(savedComment, CommentDTO.class);
    }

    @Override
    public void deleteComment(Integer commentId) {

        Comment comment = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotfoundException("Comment", "commentId", commentId));

        this.commentRepo.delete(comment);

    }
}
