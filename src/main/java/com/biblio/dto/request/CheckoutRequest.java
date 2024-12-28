package com.biblio.dto.request;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CheckoutRequest {
    private List<CheckoutItemRequest> items;
}
