package com.biblio.dto.request;

import com.biblio.dto.response.CustomerDetailResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {

    private CustomerDetailResponse customer;
    private List<CheckoutItemRequest> items;
    private Long addressId;
    private double vat = 0.1;
    private double amount;
    private String note;
    private String paymentType;
    private String bankAccountNumber;
    private String bankName;
    private String transactionId;
    private String createdAt;
    private double shippingFee = 30000;
    private List<Long> promotions = new ArrayList<>();

}
