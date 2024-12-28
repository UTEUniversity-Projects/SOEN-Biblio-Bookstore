package com.biblio.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ShippingResponse {
    private Long id;
    private AddressResponse address;
    private String shippingUnit;
    private double shippingFee;
}
