package com.example.blogs.blogsphere.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(length = 1000)
    private String content;
    
    private Date createdAt;

    // Many comments belong to one post (bidirectional mapping)
    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonBackReference // to avoid infinite recursion during serialization
    private Post post;

    // Many comments belong to one user (unidirectional mapping)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Constructor, Getters and Setters
    public Comment() {}

    public Long getCommentId() { return commentId; }
    public void setCommentId(Long commentId) { this.commentId = commentId; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Post getPost() { return post; }
    public void setPost(Post post) { this.post = post; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
