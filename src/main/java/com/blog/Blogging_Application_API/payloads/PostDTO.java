package com.blog.Blogging_Application_API.payloads;

import com.blog.Blogging_Application_API.entities.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class PostDTO {

    private Integer postId;

    private String title;

    private String content;

    private String imageName;

    private Date addDate;

    private CategoryDTO category;

    private UserDTO user;

    private List<CommentDTO> comments = new ArrayList<>();

}
