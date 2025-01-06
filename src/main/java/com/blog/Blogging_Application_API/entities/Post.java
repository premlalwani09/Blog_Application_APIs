package com.blog.Blogging_Application_API.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
@Table(name="post")
@Getter
@Setter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    @Column(name = "post_title", length = 100, nullable = false)
    private String title;

    @Column(length = 10000)
    private String content;

    private String imageName;

    private Date addDate;

    @ManyToOne
    @JoinColumn(name = "category_Id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_Id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

}
