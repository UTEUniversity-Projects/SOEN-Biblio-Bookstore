package com.biblio.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UpdateCartItemRequest {
    private Long cartItemId;
    private Long quantity;
}
