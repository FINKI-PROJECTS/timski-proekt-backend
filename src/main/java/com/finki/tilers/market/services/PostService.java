package com.finki.tilers.market.services;

import com.finki.tilers.market.exception.CustomBadRequestException;
import com.finki.tilers.market.exception.UnauthorizedAccessException;
import com.finki.tilers.market.model.entity.Post;
import com.finki.tilers.market.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public List<Post> getPostsByUserId(Long userId) {
        return postRepository.findAllByCreatedUser_Id(userId);
    }

    public Post createOrUpdatePost(Post post) {
        Post originalPost;
        if (post.getId() != null) {
            originalPost = postRepository.findById(post.getId())
                    .orElseThrow(() -> new CustomBadRequestException("Post not found"));

            if (!originalPost.getCreatedUser().getId().equals(userService.getCurrentUser().getId())) {
                throw new UnauthorizedAccessException("User is not authorized to update this product");
            }
        } else {
            originalPost = new Post();
        }

        originalPost.setName(post.getName());
        originalPost.setPrice(post.getPrice());
        originalPost.setCategory(post.getCategory());
        originalPost.setDescription(post.getDescription());
        originalPost.setThumbnail(post.getThumbnail());

        originalPost.setCreatedUser(userService.getCurrentUser());

        return postRepository.save(originalPost);
    }

}
