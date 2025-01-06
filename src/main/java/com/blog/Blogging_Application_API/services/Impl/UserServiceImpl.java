package com.blog.Blogging_Application_API.services.Impl;

import com.blog.Blogging_Application_API.config.AppConstants;
import com.blog.Blogging_Application_API.entities.Role;
import com.blog.Blogging_Application_API.entities.User;
import com.blog.Blogging_Application_API.exceptions.ResourceNotfoundException;
import com.blog.Blogging_Application_API.payloads.UserDTO;
import com.blog.Blogging_Application_API.repositories.RoleRepo;
import com.blog.Blogging_Application_API.repositories.UserRepo;
import com.blog.Blogging_Application_API.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;




    @Override
    public UserDTO registerNewUser(UserDTO userDTO) {

        // Check if password is not null
        if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }


        User user = this.modelMapper.map(userDTO, User.class);

        // encoded the password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));


        // roles
        Role role = this.roleRepo.findById(AppConstants.ROLE_NORMAL).get();

        user.getRoles().add(role);

        User newUser = this.userRepo.save(user);

        return this.modelMapper.map(newUser, UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUsers() {

        List<User> users = this.userRepo.findAll();

        List<UserDTO> userDTOs = users.stream().map(user -> this.UserToDTO(user)).collect(Collectors.toList());

        return userDTOs;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {

        User user = this.DTOToUser(userDTO);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = this.userRepo.save(user);

        return this.UserToDTO(savedUser);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, Integer userId) {

        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotfoundException("User", "Id", userId));

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAbout(userDTO.getAbout());

        User updatedUser = this.userRepo.save(user);

        return this.UserToDTO(updatedUser);
    }

    @Override
    public UserDTO getUserById(Integer userId) {

        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotfoundException("User", "Id", userId));

        return this.UserToDTO(user);
    }

    @Override
    public void deleteUser(Integer userId) {

        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotfoundException("User", "Id", userId));

        this.userRepo.delete(user);

    }

    public User DTOToUser(UserDTO userDTO){

        User user = this.modelMapper.map(userDTO, User.class);

//        User user = new User();
//
//        user.setId(userDTO.getId());
//        user.setName(userDTO.getName());
//        user.setEmail(userDTO.getEmail());
//        user.setPassword(userDTO.getPassword());
//        user.setAbout(userDTO.getAbout());

        return user;
    }

    public UserDTO UserToDTO(User user){

        UserDTO userDTO = this.modelMapper.map(user, UserDTO.class);

//        UserDTO userDTO = new UserDTO();
//
//        userDTO.setId(user.getId());
//        userDTO.setName(user.getName());
//        userDTO.setEmail(user.getEmail());
//        userDTO.setPassword(user.getPassword());
//        userDTO.setAbout(user.getAbout());

        return userDTO;
    }
}
