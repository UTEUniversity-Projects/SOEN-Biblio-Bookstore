package com.biblio.dto.response;

import com.biblio.entity.Review;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ReviewResponse {
    private Long id;
    private String imageUrl;
    private String customerName;
    private int rating;
    private String createdAt;
    private String content;
    private String responseContent;
    private boolean isHidden;

    public boolean getIsHidden() {
        return isHidden;
    }
}
