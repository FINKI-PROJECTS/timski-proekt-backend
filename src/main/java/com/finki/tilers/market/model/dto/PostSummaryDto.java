package com.finki.tilers.market.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostSummaryDto {
    private Long postId;
    private String name;
    private String price;
    private String thumbnail;
    private String description;
    private String category;
    private UserSummaryDto createdUser;
}
