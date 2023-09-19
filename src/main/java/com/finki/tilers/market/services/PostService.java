package com.finki.tilers.market.services;

import com.finki.tilers.market.exception.CustomBadRequestException;
import com.finki.tilers.market.exception.UnauthorizedAccessException;
import com.finki.tilers.market.model.dto.PostSummaryDto;
import com.finki.tilers.market.model.dto.SingleUserPostDto;
import com.finki.tilers.market.model.entity.Post;
import com.finki.tilers.market.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    public List<PostSummaryDto> getAllPosts() {
        return postRepository.findAll().stream().map(Post::mapToPostSummaryDto).collect(Collectors.toList());
    }

    public SingleUserPostDto getPostsByUserId(Long userId) {
        SingleUserPostDto userPostDto = new SingleUserPostDto();
        userPostDto.setPosts(postRepository.findAllByCreatedUser_Id(userId).stream().map(Post::mapToPostSummaryDto).collect(Collectors.toList()));
        userPostDto.setUser(userService.getUserById(userId).mapToSummaryDto());
        return userPostDto;
    }


    public PostSummaryDto getPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new CustomBadRequestException("Product with id: " + postId + "doesn't exist!")).mapToPostSummaryDto();
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

    public List<PostSummaryDto> getLatest4() {
        return postRepository.findTop4ByOrderByIdDesc().stream().map(Post::mapToPostSummaryDto).collect(Collectors.toList());
    }
}
