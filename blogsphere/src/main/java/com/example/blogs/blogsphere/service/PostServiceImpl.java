package com.example.blogs.blogsphere.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.blogs.blogsphere.DAO.PostDAO;
import com.example.blogs.blogsphere.DAO.TagDAO;
import com.example.blogs.blogsphere.entity.Post;
import com.example.blogs.blogsphere.entity.Tag;

@Service
public class PostServiceImpl implements PostService {

    private final PostDAO postDAO;
    private final TagDAO tagDAO;

    public PostServiceImpl(PostDAO postDAO, TagDAO tagDAO) {
        this.postDAO = postDAO;
        this.tagDAO = tagDAO;
    }

    // Retrieve all posts.
    @Override
    public List<Post> getAllPosts() {
        return postDAO.findAll();
    }

    // Retrieve a post by its ID.
    @Override
    public Post getPostById(Long postId) {
        return postDAO.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
    }

    // Create a new post.
    @Override
    @Transactional
    public Post createPost(Post post) {
        post.setPublishDate(new Date()); // Set the publish date to the current date.
        return postDAO.save(post);
    }

    // Update an existing post.
    @Override
    @Transactional
    public Post updatePost(Long postId, Post updatedPost) {
        Post existingPost = getPostById(postId);
        existingPost.setTitle(updatedPost.getTitle());
        existingPost.setContent(updatedPost.getContent());
        existingPost.setLikeCount(updatedPost.getLikeCount());
        existingPost.setTags(updatedPost.getTags());
        return postDAO.save(existingPost);
    }

    // Delete a post by its ID.
    @Override
    @Transactional
    public void deletePost(Long postId) {
        postDAO.deleteById(postId);
    }

    // Add a tag to a post
    @Override
    @Transactional
    public Post addTagToPost(Long postId, Long tagId) {
        Post post = getPostById(postId);
        Tag tag = tagDAO.findById(tagId)
                .orElseThrow(() -> new RuntimeException("Tag not found with id: " + tagId));
        // post.addTag(tag);
        post.getTags().add(tag);
        tag.getPosts().add(post);
        return postDAO.save(post);
    }

    // Remove a tag from a post
    @Override
    @Transactional
    public Post removeTagFromPost(Long postId, Long tagId) {
        Post post = getPostById(postId);
        Tag tag = tagDAO.findById(tagId)
                .orElseThrow(() -> new RuntimeException("Tag not found with id: " + tagId));
        // post.removeTag(tag);
        post.getTags().remove(tag);
        tag.getPosts().remove(post);
        return postDAO.save(post);
    }

    // Retrieve all posts by a specific tag name.
    @Override
    public List<Post> getPostsByTagName(String tagName) {
        return postDAO.findPostsByTagName(tagName);
    }
}
