package com.biblio.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PromotionTargetResponse {
    private Long id;
    private Long applicableObjectId;
    private String type;
}
