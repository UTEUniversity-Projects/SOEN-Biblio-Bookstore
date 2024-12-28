package com.biblio.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ReturnBookItemResponse {
    private String title;
    private int quantity;
}
