package com.biblio.mapper;

import com.biblio.dto.request.ReturnBookRequest;
import com.biblio.dto.request.ReturnOrderRequest;
import com.biblio.dto.response.ReturnBookItemResponse;
import com.biblio.dto.response.ReturnBookManagementResponse;
import com.biblio.entity.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.HashSet;


import static com.biblio.utils.DateTimeUtil.formatDateTime;

public class ReturnBookMapper {
    public static ReturnBookManagementResponse toReturnBookManagementResponse(ReturnBook returnBook) {

        List<String> fileNames = returnBook.getProof().stream()
                .sorted(Comparator.comparing(MediaFile::getId))
                .map(MediaFile::getStoredCode)
                .toList();

        List<ReturnBookItemResponse> items = new ArrayList<>();
        for (ReturnBookItem item : returnBook.getReturnBookItems()) {
            items.add(ReturnBookItemMapper.toReturnBookItemResponse(item));
        }

        return ReturnBookManagementResponse.builder()
                .id(returnBook.getId())
                .items(items)
                .reason(returnBook.getReason())
                .description(returnBook.getDescription())
                .createdAt(formatDateTime(returnBook.getCreatedAt(), "HH:mm dd-MM-yyyy"))
                .proof(fileNames)
                .build();
    }

    public static ReturnBook toEntity(ReturnOrderRequest request) {
        ReturnBook returnBook = new ReturnBook();
        returnBook.setDescription(request.getDescription());
        returnBook.setReason(request.getReason());
        returnBook.setCreatedAt(LocalDateTime.now());

        return returnBook;
    }

}
