package com.biblio.dao.impl;

import com.biblio.dao.IBookMetadataDAO;
import com.biblio.entity.BookMetadata;

public class BookMetadataDAOImpl extends GenericDAOImpl<BookMetadata> implements IBookMetadataDAO {
    public BookMetadataDAOImpl() {
        super(BookMetadata.class);
    }

    @Override
    public BookMetadata createBookMetadata(BookMetadata bookMetadata) {
        return super.insert(bookMetadata);
    }

    
}
