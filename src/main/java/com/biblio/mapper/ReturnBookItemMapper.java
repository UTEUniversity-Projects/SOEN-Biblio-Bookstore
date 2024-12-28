package com.biblio.mapper;

import com.biblio.dto.response.ReturnBookItemResponse;
import com.biblio.entity.Book;
import com.biblio.entity.ReturnBookItem;

import java.util.Set;

public class ReturnBookItemMapper {
    public static ReturnBookItemResponse toReturnBookItemResponse(ReturnBookItem returnBookItem) {

        Set<Book> books = returnBookItem.getBooks();
        Book singleBook = books.iterator().next();

        return ReturnBookItemResponse.builder()
                .title(singleBook.getTitle())
                .quantity(books.size())
                .build();
    }
}
