package com.blog.Blogging_Application_API.services.Impl;

import com.blog.Blogging_Application_API.entities.Category;
import com.blog.Blogging_Application_API.entities.Post;
import com.blog.Blogging_Application_API.entities.User;
import com.blog.Blogging_Application_API.exceptions.ResourceNotfoundException;
import com.blog.Blogging_Application_API.payloads.PostDTO;
import com.blog.Blogging_Application_API.payloads.PostResponse;
import com.blog.Blogging_Application_API.repositories.CategoryRepo;
import com.blog.Blogging_Application_API.repositories.PostRepo;
import com.blog.Blogging_Application_API.repositories.UserRepo;
import com.blog.Blogging_Application_API.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;



    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

//        if (sortDir.equalsIgnoreCase("asc")){
//            sort = Sort.by(sortBy).ascending();
//        }
//        else {
//            sort = Sort.by(sortBy).descending();
//        }

        Pageable p = PageRequest.of(pageNumber, pageSize, sort);

        Page<Post> pageOfPost = this.postRepo.findAll(p);

        List<Post> allPosts = pageOfPost.getContent();

        List<PostDTO> allPostDTOS = allPosts.stream().map((post) -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();

        postResponse.setContent(allPostDTOS);
        postResponse.setPageNumber(pageOfPost.getNumber());
        postResponse.setPageSize(pageOfPost.getSize());
        postResponse.setTotalElements(pageOfPost.getTotalElements());
        postResponse.setTotalPages(pageOfPost.getTotalPages());
        postResponse.setLastPage(pageOfPost.isLast());

        return postResponse;
    }

    @Override
    public PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId) {

        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotfoundException("User", "userId", userId));

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotfoundException("Category", "categoryId", categoryId));

        Post post = this.modelMapper.map(postDTO, Post.class);

        post.setImageName("default.png");
        post.setAddDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post newPost = this.postRepo.save(post);

        return this.modelMapper.map(newPost, PostDTO.class);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Integer postId) {

        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotfoundException("Post", "postId", postId));

        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setImageName(postDTO.getImageName());

        Post updatedPost = this.postRepo.save(post);

        return this.modelMapper.map(updatedPost, PostDTO.class);
    }

    @Override
    public PostResponse getPostByIds(List<Integer> postIds, int pageNumber, int pageSize) {

        Pageable p = PageRequest.of(pageNumber, pageSize);

        Page<Post> pagePosts = postRepo.findByPostIdIn(postIds, p);

        List<PostDTO> postDTOs = pagePosts.getContent().stream().map((post) -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();

        postResponse.setContent(postDTOs);
        postResponse.setPageNumber(pagePosts.getNumber());
        postResponse.setPageSize(pagePosts.getSize());
        postResponse.setTotalElements(pagePosts.getTotalElements());
        postResponse.setTotalPages(pagePosts.getTotalPages());
        postResponse.setLastPage(pagePosts.isLast());

        return postResponse;
    }

    @Override
    public PostDTO getPostById(Integer postId) {

        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotfoundException("Post", "postId", postId));

        return this.modelMapper.map(post, PostDTO.class);

    }

    @Override
    public void deletePost(Integer postId) {

        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotfoundException("Post", "postId", postId));

        this.postRepo.delete(post);

    }

    @Override
    public PostResponse getPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize) {

        Pageable p = PageRequest.of(pageNumber, pageSize);

        Page<Post> pageOfPost = this.postRepo.findByCategoryCategoryId(categoryId, p);

        List<PostDTO> postDTOSByCategory = pageOfPost.getContent().stream().map((post) -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());

        PostResponse postResponsesByCategory = new PostResponse();

        postResponsesByCategory.setContent(postDTOSByCategory);
        postResponsesByCategory.setPageNumber(pageOfPost.getNumber());
        postResponsesByCategory.setPageSize(pageOfPost.getSize());
        postResponsesByCategory.setTotalElements(pageOfPost.getTotalElements());
        postResponsesByCategory.setTotalPages(pageOfPost.getTotalPages());
        postResponsesByCategory.setLastPage(pageOfPost.isLast());

        return postResponsesByCategory;
    }

    @Override
    public PostResponse getPostsByUser(Integer userId, int pageNumber, int pageSize) {

        Pageable p = PageRequest.of(pageNumber, pageSize);

        Page<Post> pageOfPost = this.postRepo.findByUserId(userId, p);

        List<PostDTO> postDTOSByUser = pageOfPost.getContent().stream().map((post) -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());

        PostResponse postResponsesByUser = new PostResponse();

        postResponsesByUser.setContent(postDTOSByUser);
        postResponsesByUser.setPageNumber(pageOfPost.getNumber());
        postResponsesByUser.setPageSize(pageOfPost.getSize());
        postResponsesByUser.setTotalElements(pageOfPost.getTotalElements());
        postResponsesByUser.setTotalPages(pageOfPost.getTotalPages());
        postResponsesByUser.setLastPage(pageOfPost.isLast());

        return postResponsesByUser;
    }

    @Override
    public List<PostDTO> searchPosts(String keyword) {

        List<Post> posts = this.postRepo.findByTitleContaining(keyword);

        List<PostDTO> postDTOList = posts.stream().map((post) -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());

        return postDTOList;
    }
}
