package com.biblio.mapper;

import com.biblio.dto.request.SupportRequest;
import com.biblio.entity.Customer;
import com.biblio.entity.Support;

import java.time.LocalDateTime;

public class SupportMapper {
    public static Support toEntity(SupportRequest request, Customer customer) {
        if (request == null || customer == null) {
            throw new IllegalArgumentException("Request hoặc Customer không hợp lệ");
        }

        Support support = new Support();
        support.setTitle(request.getTitle());
        support.setContent(request.getFeedbackContent());
        support.setCustomer(customer);
        support.setCreatedAt(LocalDateTime.now());
        return support;
    }
}
