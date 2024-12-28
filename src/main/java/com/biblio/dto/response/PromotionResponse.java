package com.biblio.dto.response;

import com.biblio.enumeration.EPromotionStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class PromotionResponse {
    private Long id;
    private String title;
    private String description;

    private double percentDiscount;
    private double discountLimit;
    private double minValueApplied;

    private String effectiveDate;
    private String expirationDate;
    private EPromotionStatus status;
    private Set<PromotionTargetResponse> promotionTargets = new HashSet<>();
}
