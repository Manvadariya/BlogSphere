package com.example.blogs.blogsphere.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private String title;

    @Lob
    private String content;

    private Date publishDate;
    private int likeCount;

    // ManyToOne relationship with User entity - unidirectional mapping
    // Many posts belong to one user (author)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // One post can have many comments (bidirectional)
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @JsonManagedReference // to avoid infinite recursion during serialization
    private List<Comment> comments = new ArrayList<>();

    // Many-to-Many relationship with tags (bidirectional)
    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(
        name = "post_tags",
        joinColumns = @JoinColumn(name = "post_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    public Post() {}

    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Date getPublishDate() { return publishDate; }
    public void setPublishDate(Date publishDate) { this.publishDate = publishDate; }
    public int getLikeCount() { return likeCount; }
    public void setLikeCount(int likeCount) { this.likeCount = likeCount; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public List<Comment> getComments() { return comments; }
    public void setComments(List<Comment> comments) { this.comments = comments; }
    public Set<Tag> getTags() { return tags; }
    public void setTags(Set<Tag> tags) { this.tags = tags; }
}
