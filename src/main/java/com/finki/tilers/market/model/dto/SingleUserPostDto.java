package com.finki.tilers.market.model.dto;

import lombok.*;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SingleUserPostDto {
    private List<PostSummaryDto> posts;
    private UserSummaryDto user;
}
