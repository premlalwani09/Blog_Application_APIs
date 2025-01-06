package com.blog.Blogging_Application_API.services;

import com.blog.Blogging_Application_API.payloads.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO registerNewUser(UserDTO userDTO);

    public List<UserDTO> getAllUsers();

    public UserDTO createUser(UserDTO user);

    public UserDTO updateUser(UserDTO user, Integer userId);

    public UserDTO getUserById(Integer userId);

    public void deleteUser(Integer userId);
}
