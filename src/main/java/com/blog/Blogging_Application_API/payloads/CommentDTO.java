package com.blog.Blogging_Application_API.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {

    private int id;

    private String content;

    private int userId;

}
