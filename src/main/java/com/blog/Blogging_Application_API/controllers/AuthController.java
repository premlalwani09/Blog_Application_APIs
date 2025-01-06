package com.blog.Blogging_Application_API.controllers;

import com.blog.Blogging_Application_API.payloads.ErrorResponse;
import com.blog.Blogging_Application_API.payloads.JWTAuthRequest;
import com.blog.Blogging_Application_API.payloads.JWTAuthResponse;
import com.blog.Blogging_Application_API.payloads.UserDTO;
import com.blog.Blogging_Application_API.security.JWTTokenHelper;
import com.blog.Blogging_Application_API.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

    @Autowired
    private JWTTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;





    @PostMapping("/login")
    public ResponseEntity<?> createToken(@RequestBody JWTAuthRequest request) {

        try {
            // Authenticate user credentials
            this.authenticate(request.getUsername(), request.getPassword());

            // Load user details if authentication is successful
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());

            // Generate JWT token
            String token = this.jwtTokenHelper.generateToken(userDetails);

            // Return token in the response
            JWTAuthResponse response = new JWTAuthResponse();
            response.setToken(token);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (BadCredentialsException e) {
            // Return an error response if credentials are invalid
            ErrorResponse errorResponse = new ErrorResponse("Invalid username or password");

            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        }
        catch (Exception e) {
            // Handle any other exceptions
            ErrorResponse errorResponse = new ErrorResponse("An unexpected error occurred");

            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private void authenticate(String username, String password) throws Exception {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        try {
            this.authenticationManager.authenticate(authenticationToken);
        }
        catch (BadCredentialsException e){
            System.out.println("Invalid Details !!");
            throw e;
        }
    }



    // Register New User API
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO){

        // Debug Log
        System.out.println("Received Password: " + userDTO.getPassword());

        UserDTO registerUser = this.userService.registerNewUser(userDTO);

        return new ResponseEntity<UserDTO>(registerUser, HttpStatus.CREATED);
    }
}
