package com.blog.Blogging_Application_API.controllers;

import com.blog.Blogging_Application_API.payloads.ApiResponse;
import com.blog.Blogging_Application_API.payloads.UserDTO;
import com.blog.Blogging_Application_API.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // GET - get all users
    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUsers(){

        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    // POST - create user
    @PostMapping("/")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO){

        UserDTO createUserDTO = this.userService.createUser(userDTO);
        return new ResponseEntity<>(createUserDTO, HttpStatus.CREATED);
    }

    // PUT - update user
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable("userId") Integer uid){

        UserDTO updatedUser = this.userService.updateUser(userDTO, uid);
        return ResponseEntity.ok(updatedUser);
    }

    // GET - user get by id
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer userId){

        return ResponseEntity.ok(this.userService.getUserById(userId));
    }

    // ADMIN
    // DELETE - delete user
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid){

        this.userService.deleteUser(uid);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully!", true), HttpStatus.OK);
    }
}
