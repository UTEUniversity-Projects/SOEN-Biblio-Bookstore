package com.biblio.dto.response;

import com.biblio.enumeration.EOrderHistory;
import com.biblio.enumeration.EOrderStatus;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderStatusHistoryResponse {
    EOrderHistory status;
    String date;
}
