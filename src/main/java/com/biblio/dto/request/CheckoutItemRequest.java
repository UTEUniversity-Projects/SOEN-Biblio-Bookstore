package com.biblio.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CheckoutItemRequest {
    Long productId;
    int quantity;
}
