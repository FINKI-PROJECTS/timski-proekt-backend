package com.finki.tilers.market.model.entity;

import com.finki.tilers.market.model.dto.PostSummaryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name must be provided")
    @NotEmpty(message = "Name must be a long string")
    private String name;

    private String price;

    @Column(nullable = false)
    private String thumbnail;

    @Lob
    @Column(columnDefinition = "TEXT")
    @NotNull(message = "Name must be provided")
    @NotEmpty(message = "Name must be a long string")
    private String description;

    private String category;

    @ManyToOne
    @JoinColumn(name = "created_user_id")
    private ApplicationUser createdUser;


    public PostSummaryDto mapToPostSummaryDto() {
        PostSummaryDto dto = new PostSummaryDto();
        dto.setPostId(this.getId());
        dto.setName(this.getName());
        dto.setPrice(this.getPrice());
        dto.setThumbnail(this.getThumbnail());
        dto.setDescription(this.getDescription());
        dto.setCategory(this.getCategory());

        dto.setCreatedUser(this.getCreatedUser().mapToSummaryDto());

        return dto;
    }
}
