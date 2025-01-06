package com.blog.Blogging_Application_API.controllers;

import com.blog.Blogging_Application_API.config.AppConstants;
import com.blog.Blogging_Application_API.payloads.ApiResponse;
import com.blog.Blogging_Application_API.payloads.PostDTO;
import com.blog.Blogging_Application_API.payloads.PostResponse;
import com.blog.Blogging_Application_API.services.FileService;
import com.blog.Blogging_Application_API.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;



    // Get All Post
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber, @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize, @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy, @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir){

        PostResponse allPosts = this.postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);

        return new ResponseEntity<PostResponse>(allPosts, HttpStatus.OK);
    }

    // Create Post
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO, @PathVariable Integer userId, @PathVariable Integer categoryId){

        PostDTO createPost = this.postService.createPost(postDTO, userId, categoryId);

        return new ResponseEntity<PostDTO>(createPost, HttpStatus.CREATED);
    }

    // Update Post By Id
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDTO> updatePostById(@RequestBody PostDTO postDTO, @PathVariable Integer postId){

        PostDTO updatedPost = this.postService.updatePost(postDTO, postId);

        return new ResponseEntity<PostDTO>(updatedPost, HttpStatus.OK);
    }

    // Get Post By Id
    @GetMapping("/posts/by-ids")
    public ResponseEntity<PostResponse> getPostByIds(@RequestParam List<Integer> postIds, @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber, @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize){

        PostResponse post = this.postService.getPostByIds(postIds, pageNumber, pageSize);

        return new ResponseEntity<PostResponse>(post, HttpStatus.OK);
    }

    // Delete Post By Id
    @DeleteMapping("/posts/{postId}")
    public ApiResponse deletePostByd(@PathVariable Integer postId){

        this.postService.deletePost(postId);

        return new ApiResponse("Post is Deleted Successfully!", true);
    }

    // Get All Post By Category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<PostResponse> getAllPostsByCategory(@PathVariable Integer categoryId, @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber, @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize){

        PostResponse postResponseByCategory = this.postService.getPostsByCategory(categoryId, pageNumber, pageSize);

        return new ResponseEntity<PostResponse>(postResponseByCategory, HttpStatus.OK);
    }

    // Get All Posts By User
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<PostResponse> getAllPostsByUser(@PathVariable Integer userId, @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber, @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize){

        PostResponse postResponsesByUser = this.postService.getPostsByUser(userId, pageNumber, pageSize);

        return new ResponseEntity<PostResponse>(postResponsesByUser, HttpStatus.OK);
    }

    // Search Post By Keyword
    @GetMapping("/posts/search/{keywords}")
    public  ResponseEntity<List<PostDTO>> searchPostByTitle(@PathVariable String keywords){

        List<PostDTO> result = this.postService.searchPosts(keywords);

        return new ResponseEntity<List<PostDTO>>(result, HttpStatus.OK);
    }


    // Post Image Upload
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDTO> uploadPostImage(@RequestParam MultipartFile image, @PathVariable Integer postId) throws IOException {

        PostDTO postDTO = this.postService.getPostById(postId);

        String fileName = this.fileService.uploadImage(path, image);

        postDTO.setImageName(fileName);

        PostDTO updatePost = this.postService.updatePost(postDTO, postId);

        return new ResponseEntity<PostDTO>(updatePost, HttpStatus.OK);
    }


    // Method to Serve Files
    @GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {

        InputStream resource = this.fileService.getResource(path, imageName);

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(resource, response.getOutputStream());
    }

}
