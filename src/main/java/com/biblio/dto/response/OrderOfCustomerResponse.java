package com.biblio.dto.response;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
@Setter
public class OrderOfCustomerResponse {
    private Long customerId;
    private String customerName;
    private Long orderId;
}
