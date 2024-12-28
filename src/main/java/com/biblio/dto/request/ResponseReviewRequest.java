package com.biblio.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class ResponseReviewRequest {
    private String content;
    private Long reviewId;
}
