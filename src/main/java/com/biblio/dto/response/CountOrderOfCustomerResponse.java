package com.biblio.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
@Setter
public class CountOrderOfCustomerResponse {
    private Long customerId;
    private String customerName;
    private Long countOrders;
}
