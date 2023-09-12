package com.finki.tilers.market.repositories;

import com.finki.tilers.market.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByCreatedUser_Id(Long userId);
}
