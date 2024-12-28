package com.biblio.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ReviewGetListResponse {
    private Long id;
    private String content;
    private String createdAt;
}
