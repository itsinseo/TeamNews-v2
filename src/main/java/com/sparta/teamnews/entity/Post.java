package com.sparta.teamnews.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "posts")
@NoArgsConstructor
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(name = "title")
    private String title;


    @Column(name = "content", nullable = false, length = 500)
    private String content;

    private String originalName;

    private String savedName;

    private String savedPath;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = {CascadeType.REMOVE})
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = {CascadeType.REMOVE})
    private List<Like> likeList = new ArrayList<>();

    public Post(String title, String content, String originalName, String savedName, String savedPath, User user) {
        this.title = title;
        this.content = content;
        this.originalName = originalName;
        this.savedName = savedName;
        this.savedPath = savedPath;
        this.user = user;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
