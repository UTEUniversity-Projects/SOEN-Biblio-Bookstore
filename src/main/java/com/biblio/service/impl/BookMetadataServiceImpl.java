package com.biblio.service.impl;

import com.biblio.dao.IBookMetadataDAO;
import com.biblio.dto.request.BookCreateGlobalRequest;
import com.biblio.dto.request.BookUpdateGlobalRequest;
import com.biblio.entity.BookMetadata;
import com.biblio.mapper.BookMetadataMapper;
import com.biblio.service.IBookMetadataService;

import javax.inject.Inject;

public class BookMetadataServiceImpl implements IBookMetadataService {

    @Inject
    IBookMetadataDAO bookMetadataDAO;

    @Override
    public BookMetadata createBookMetadata(BookCreateGlobalRequest request) {
        return bookMetadataDAO.createBookMetadata(BookMetadataMapper.toBookMetadata(request));
    }

    @Override
    public BookMetadata createBookMetadata(BookUpdateGlobalRequest request) {
        return bookMetadataDAO.createBookMetadata(BookMetadataMapper.toBookMetadata(request));
    }
}
