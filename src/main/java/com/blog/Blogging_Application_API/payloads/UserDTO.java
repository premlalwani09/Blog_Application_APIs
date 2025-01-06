package com.blog.Blogging_Application_API.payloads;

import com.blog.Blogging_Application_API.entities.Comment;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    private int id;

    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 4, message = "Username must be minimum of 4 characters")
    private String name;

    @Email(message = "Email address is not Valid!")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Only available in request, hidden in response
    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 3, max = 10, message = "Password must be minimum of 3 characters and maximum of 10 characters!")
    private String password;

    @NotEmpty(message = "About section cannot be empty")
    private String about;

    private Set<RoleDTO> roles = new HashSet<>();

    private List<CommentDTO> comments = new ArrayList<>();
}
