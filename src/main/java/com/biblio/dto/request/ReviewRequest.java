package com.biblio.dto.request;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReviewRequest {
    private String content;
    private int rate;
    private Long bookTemplateId;
    private boolean readyToIntroduce;
    private Long orderId;
}
