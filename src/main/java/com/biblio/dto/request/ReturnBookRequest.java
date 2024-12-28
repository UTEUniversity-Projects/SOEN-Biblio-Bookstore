package com.biblio.dto.request;

import com.biblio.enumeration.EReasonReturn;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReturnBookRequest {
    private Long bookTemplateId;
    private int quantity;
}
