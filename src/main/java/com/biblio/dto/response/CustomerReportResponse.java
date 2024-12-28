package com.biblio.dto.response;

import lombok.*;

import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CustomerReportResponse {
    private Long id;
    private LocalDateTime joinAt;
}
