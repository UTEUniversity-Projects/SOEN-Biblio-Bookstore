package com.biblio.service;

import com.biblio.dto.request.BookCreateGlobalRequest;
import com.biblio.dto.request.BookUpdateGlobalRequest;
import com.biblio.entity.BookMetadata;

public interface IBookMetadataService {
    BookMetadata createBookMetadata(BookCreateGlobalRequest request);
    BookMetadata createBookMetadata(BookUpdateGlobalRequest request);
}
