package com.biblio.service.impl;

import com.biblio.dao.IBookDAO;
import com.biblio.dao.IBookTemplateDAO;
import com.biblio.dao.IOrderDAO;
import com.biblio.dao.ISubCategoryDAO;
import com.biblio.dto.request.BookCreateGlobalRequest;
import com.biblio.dto.request.BookRequest;
import com.biblio.dto.request.BookUpdateGlobalRequest;
import com.biblio.dto.response.BookResponse;
import com.biblio.entity.Book;
import com.biblio.entity.BookMetadata;
import com.biblio.entity.BookTemplate;
import com.biblio.entity.SubCategory;
import com.biblio.enumeration.EBookMetadataStatus;
import com.biblio.mapper.BookMapper;
import com.biblio.service.IBookMetadataService;
import com.biblio.service.IBookService;
import com.biblio.service.IBookTemplateService;
import com.biblio.service.ISubCategoryService;

import javax.inject.Inject;
import java.util.List;

public class BookServiceImpl implements IBookService {
    @Inject
    private IBookDAO bookDAO;

    @Inject
    private IBookTemplateDAO bookTemplateDAO;

    @Inject
    private IOrderDAO orderDAO;

    @Inject
    private ISubCategoryService subCategoryService;

    @Inject
    private IBookTemplateService bookTemplateService;

    @Inject
    private IBookMetadataService bookMetadataService;

    @Override
    public List<BookResponse> findAll() {
        return List.of();
    }

    @Override
    public BookTemplate createBookSeries(BookCreateGlobalRequest bookCreateRequest) {
        long quantity = Long.parseLong(bookCreateRequest.getQuantity());

        SubCategory subCategory = subCategoryService.getEntityById(Long.valueOf(bookCreateRequest.getSubCategoryId()));
        BookTemplate bookTemplate = bookTemplateService.createBookTemplate(bookCreateRequest);

        for (int i = 0; i < quantity; i++) {
            BookMetadata bookMetadata = bookMetadataService.createBookMetadata(bookCreateRequest);
            bookDAO.createBook(BookMapper.toBookEntity(bookCreateRequest,
                    bookTemplate, subCategory, bookMetadata));
        }

        return bookTemplate;
    }

    @Override
    public BookTemplate updateBookSeries(BookUpdateGlobalRequest bookUpdateRequest) {
        BookTemplate oldBookTemplate = bookTemplateDAO.findOneForDetails(Long.valueOf(bookUpdateRequest.getId()));
        BookTemplate newBookTemplate = bookTemplateService.updateBookTemplate(bookUpdateRequest);
        SubCategory subCategory = subCategoryService.getEntityById(Long.valueOf(bookUpdateRequest.getSubCategoryId()));

        long oldQuantity = bookTemplateDAO.countInstockById(oldBookTemplate.getId());
        long newQuantity = Long.parseLong(bookUpdateRequest.getQuantity());

        List<Book> books = oldBookTemplate.getBooks().stream()
                .filter(book -> book.getBookMetadata().getStatus() == EBookMetadataStatus.IN_STOCK).toList();

        if (oldQuantity < newQuantity) {
            for (Book book : books) {
                BookMetadata bookMetadata = book.getBookMetadata();
                bookDAO.updateBook(BookMapper.toBookEntity(bookUpdateRequest,
                        newBookTemplate, subCategory, bookMetadata, book));
            }

            long quantity = newQuantity - oldQuantity;

            for (int i = 0; i < quantity; i++) {
                BookMetadata bookMetadata = bookMetadataService.createBookMetadata(bookUpdateRequest);
                bookDAO.createBook(BookMapper.toBookEntity(bookUpdateRequest,
                        newBookTemplate, subCategory, bookMetadata));
            }
        } else {
            long quantity = oldQuantity - newQuantity;

            for (int i = 0; i < quantity; i++) {
                bookDAO.deleteBook(books.get(i).getId());
            }

            for (Book book : books) {
                BookMetadata bookMetadata = book.getBookMetadata();
                bookDAO.updateBook(BookMapper.toBookEntity(bookUpdateRequest,
                        newBookTemplate, subCategory, bookMetadata, book));
            }
        }

        return newBookTemplate;
    }

    @Override
    public void addBook(BookRequest bookRequest) {

    }

    @Override
    public void updateBook(BookRequest bookRequest) {

    }

    @Override
    public void deleteBook(Long id) {

    }

    @Override
    public Long getMinBookPrice() {
        return bookDAO.findMinBookPrice();
    }

    @Override
    public Long getMaxBookPrice() {
        return bookDAO.findMaxBookPrice();
    }

}
