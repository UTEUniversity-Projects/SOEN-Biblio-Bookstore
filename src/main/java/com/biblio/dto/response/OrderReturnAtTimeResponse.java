package com.biblio.dto.response;

import com.biblio.enumeration.EReasonReturn;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class OrderReturnAtTimeResponse {
    private Long orderId;
    private Boolean isReturned;
    private EReasonReturn returnReason;
}
