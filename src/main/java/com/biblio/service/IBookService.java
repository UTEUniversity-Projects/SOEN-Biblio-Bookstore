package com.biblio.service;

import com.biblio.dto.request.BookCreateGlobalRequest;
import com.biblio.dto.request.BookRequest;
import com.biblio.dto.request.BookUpdateGlobalRequest;
import com.biblio.dto.response.BookResponse;
import com.biblio.entity.BookTemplate;

import java.util.List;

public interface IBookService {

    List<BookResponse> findAll();
    BookTemplate createBookSeries(BookCreateGlobalRequest bookCreateGlobalRequest);
    BookTemplate updateBookSeries(BookUpdateGlobalRequest bookUpdateGlobalRequest);

    void addBook(BookRequest bookRequest);

    void updateBook(BookRequest bookRequest);

    void deleteBook(Long id);

    Long getMinBookPrice();

    Long getMaxBookPrice();
}
