package com.biblio.dto.request;

import com.biblio.enumeration.EReasonReturn;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReturnOrderRequest {
    private Long orderId;
    private EReasonReturn reason;
    private String description;
    private List<ReturnBookRequest> returnBookItems;

}
