package com.biblio.dto.response;

import com.biblio.enumeration.EReasonReturn;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ReturnBookManagementResponse {
    private Long id;
    private List<ReturnBookItemResponse> items;
    private EReasonReturn reason;
    private String description;
    private String createdAt;
    private List<String> proof;
}
