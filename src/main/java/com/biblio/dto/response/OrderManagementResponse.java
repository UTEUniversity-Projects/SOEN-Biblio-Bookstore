package com.biblio.dto.response;

import com.biblio.enumeration.EOrderStatus;
import lombok.*;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class OrderManagementResponse {
    private Long id;
    private String customerName;
    private String orderDate;
    private double totalPrice;
    private String paymentMethod;
    private EOrderStatus status;
    private String statusStyle;
}
