package com.finki.tilers.market.controllers;

import com.finki.tilers.market.model.entity.Post;
import com.finki.tilers.market.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    /**
     * Get all Posts.
     *
     * @return list of all Posts.
     */
    @GetMapping("/")
    public ResponseEntity<List<Post>> getAllPosts() {
        try {
            List<Post> Posts = postService.getAllPosts();
            if (Posts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(Posts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get Posts by a specific user.
     *
     * @param userId ID of the user.
     * @return list of Posts by the user.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<Post>> getPostsByUserId(@PathVariable Long userId) {
        List<Post> Posts = postService.getPostsByUserId(userId);
        if (Posts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(Posts, HttpStatus.OK);
    }

    /**
     * Create or update a Post.
     *
     * @param post Post details.
     * @return saved/updated Post.
     */
    @PostMapping("/createOrUpdate")
    public ResponseEntity<Post> createOrUpdatePost(@RequestBody Post post) {
        Post savedPost = postService.createOrUpdatePost(post);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }
}
