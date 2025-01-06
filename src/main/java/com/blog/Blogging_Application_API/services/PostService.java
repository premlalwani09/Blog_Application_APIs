package com.blog.Blogging_Application_API.services;

import com.blog.Blogging_Application_API.entities.Post;
import com.blog.Blogging_Application_API.payloads.PostDTO;
import com.blog.Blogging_Application_API.payloads.PostResponse;

import java.util.List;

public interface PostService {

    // Get All Post
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    // Create Post
    public PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId);

    // Update Post
    public PostDTO updatePost(PostDTO postDTO, Integer postId);

    // Get Post By Id
    public PostResponse getPostByIds(List<Integer> postIds, int pageNumber, int pageSize);
    public PostDTO getPostById(Integer postId);

    // Delete Post By Id
    public void deletePost(Integer postId);

    // Get All Post By Category
    public PostResponse getPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize);

    // Get All Post By User
    public PostResponse getPostsByUser(Integer userId, int pageNumber, int pageSize);

    // Search Post By Keyword
    public List<PostDTO> searchPosts(String keyword);
}
