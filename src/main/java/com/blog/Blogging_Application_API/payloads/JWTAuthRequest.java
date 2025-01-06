package com.blog.Blogging_Application_API.payloads;

import lombok.Data;

@Data
public class JWTAuthRequest {

    private String username;

    private String password;
}
