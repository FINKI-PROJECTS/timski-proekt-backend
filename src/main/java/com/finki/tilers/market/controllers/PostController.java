package com.finki.tilers.market.controllers;

import com.finki.tilers.market.model.dto.PostSummaryDto;
import com.finki.tilers.market.model.dto.SingleUserPostDto;
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
    public ResponseEntity<List<PostSummaryDto>> getAllPosts() {
        try {
            List<PostSummaryDto> posts = postService.getAllPosts();
            if (posts.isEmpty()) {
                return new ResponseEntity<>(List.of(), HttpStatus.OK);
            }
            return new ResponseEntity<>(posts, HttpStatus.OK);
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
    public ResponseEntity<SingleUserPostDto> getPostsByUserId(@PathVariable Long userId) {
        SingleUserPostDto posts = postService.getPostsByUserId(userId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
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

    @GetMapping("/getById/{postId}")
    public ResponseEntity<PostSummaryDto> getPostById(@PathVariable Long postId) {
        return new ResponseEntity<>(postService.getPostById(postId), HttpStatus.OK);
    }
}
