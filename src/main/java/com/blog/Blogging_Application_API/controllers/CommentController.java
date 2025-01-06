package com.blog.Blogging_Application_API.controllers;

import com.blog.Blogging_Application_API.payloads.ApiResponse;
import com.blog.Blogging_Application_API.payloads.CommentDTO;
import com.blog.Blogging_Application_API.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class CommentController {

    @Autowired
    private CommentService commentService;



    @PostMapping("/users/{userId}/post/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO comment, @PathVariable Integer postId, @PathVariable Integer userId){

        CommentDTO createComment = this.commentService.createComment(comment, postId, userId);

        return new ResponseEntity<CommentDTO>(createComment, HttpStatus.CREATED);
    }


    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){

        this.commentService.deleteComment(commentId);

        return new ResponseEntity<ApiResponse>(new ApiResponse("Comment is Successfully Deleted!", true), HttpStatus.OK);
    }


}
