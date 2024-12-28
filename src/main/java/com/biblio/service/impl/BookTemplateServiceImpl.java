package com.biblio.service.impl;

import com.biblio.dao.*;

import com.biblio.dto.request.BookCreateGlobalRequest;
import com.biblio.dto.request.BookUpdateGlobalRequest;
import com.biblio.dto.request.MediaFileCreateRequest;
import com.biblio.dto.request.SearchBookRequest;
import com.biblio.dto.response.*;

import com.biblio.entity.*;
import com.biblio.enumeration.EBookMetadataStatus;
import com.biblio.enumeration.EOrderStatus;
import com.biblio.mapper.BookMapper;
import com.biblio.dto.request.CheckoutItemRequest;
import com.biblio.dto.request.LoadRelatedBooksRequest;

import com.biblio.entity.Book;
import com.biblio.entity.BookTemplate;
import com.biblio.enumeration.EBookTemplateStatus;
import com.biblio.mapper.BookTemplateMapper;
import com.biblio.service.IBookTemplateService;
import com.biblio.service.IMediaFileService;
import com.biblio.utils.ManageFileUtil;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.*;

public class BookTemplateServiceImpl implements IBookTemplateService {

    @Inject
    IBookTemplateDAO bookTemplateDAO;

    @Inject
    ICategoryDAO categoryDAO;
    @Inject
    IAuthorDAO authorDAO;
    @Inject
    ITranslatorDAO translatorDAO;
    @Inject
    IPublisherDAO publisherDAO;
    @Inject
    IMediaFileService mediaFileService;

    @Override
    public List<BookManagementResponse> getAllBookManagementResponse() {
        List<BookTemplate> bookTemplates = bookTemplateDAO.findAllForManagement();
        List<BookManagementResponse> bookManagementResponseList = new ArrayList<>();
        for (BookTemplate bookTemplate : bookTemplates) {
            bookManagementResponseList.add(BookTemplateMapper.toBookManagementResponse(bookTemplate));
        }
        return bookManagementResponseList;
    }

    @Override
    public List<BookCardResponse> getAllBookCardResponse() {
        List<BookTemplate> bookTemplates = bookTemplateDAO.findAllForHome();
        List<BookCardResponse> bookCardResponseList = new ArrayList<>();
        for (BookTemplate bookTemplate : bookTemplates) {
            bookCardResponseList.add(BookTemplateMapper.toBookCardResponse(bookTemplate));
        }
        return bookCardResponseList;
    }

    @Override
    public BookDetailsResponse getBookDetailsResponse(Long bookTemplateId) {
        BookTemplate bookTemplate = bookTemplateDAO.findOneForDetails(bookTemplateId);
        return BookTemplateMapper.toBookDetailsResponse(bookTemplate);
    }

    @Override
    public List<BookTemplatePromotionResponse> getAllBookBookTemplatePromotionResponse() {
        List<BookTemplate> bookTemplates = bookTemplateDAO.findAll();
        List<BookTemplatePromotionResponse> bookTemplatePromotionResponse = new ArrayList<>();
        for (BookTemplate bookTemplate : bookTemplates) {
            bookTemplatePromotionResponse.add(BookTemplateMapper.toBookTemplatePromotionResponse(bookTemplate));
        }
        return bookTemplatePromotionResponse;
    }

    @Override
    public List<BookCardResponse> getBookTemplateByCriteria(SearchBookRequest request) {

        List<BookTemplate> bookTemplates = bookTemplateDAO.findByCriteria(request);
        List<BookCardResponse> bookCardResponseList = new ArrayList<>();
        for (BookTemplate bt : bookTemplates) {
            bookCardResponseList.add(BookTemplateMapper.toBookCardResponse(bt));
        }
        return bookCardResponseList;

    }

    @Override
    public List<BookSoldAllTimeResponse> getListCountBookSoldAllTime() {
        List<BookSoldAllTimeResponse> listBookSold = new ArrayList<>();
        List<BookTemplate> bookTemplateList = bookTemplateDAO.findAllForHome();
        for (BookTemplate bookTemplate : bookTemplateList) {
            BookTemplate book = bookTemplateDAO.findOneForDetails(bookTemplate.getId());
            listBookSold.add(BookTemplateMapper.toBookSoldAllTimeResponse(book));
        }

        listBookSold.sort(Comparator.comparingLong(BookSoldAllTimeResponse::getCountSold).reversed());
        return listBookSold;
    }

    @Override
    public Long getTotalBookTemplateQuantity() {
        return bookTemplateDAO.countAll();
    }

    @Override
    public BookCreateResponse initCreateBook() {
        return BookTemplateMapper.toBookCreateResponse(categoryDAO.getEntityAll(),
                authorDAO.getEntityAll(), translatorDAO.getEntityAll(), publisherDAO.getEntityAll());
    }

    @Override
    public BookUpdateResponse initUpdateBook() {
        return BookTemplateMapper.toBookUpdateResponse(categoryDAO.getEntityAll(),
                authorDAO.getEntityAll(), translatorDAO.getEntityAll(), publisherDAO.getEntityAll());
    }

    @Override
    public BookTemplate createBookTemplate(BookCreateGlobalRequest request) {
        Publisher publisher = publisherDAO.getEntityById(Long.valueOf(request.getPublisherId()));

        List<MediaFile> mediaFiles = new ArrayList<>();
        MediaFile mainImage = mediaFileService.createMediaFile(
                MediaFileCreateRequest.builder()
                        .fileName("main")
                        .storedCode(request.getMainImage())
                        .build()
        );
        mediaFiles.add(mainImage);

        for (String thumb : request.getThumbnails()) {
            mediaFiles.add(
                    mediaFileService.createMediaFile(
                            MediaFileCreateRequest.builder()
                                    .fileName("thumb")
                                    .storedCode(thumb)
                                    .build()
                    )
            );
        }

        BookTemplate bookTemplate = bookTemplateDAO.createBookTemplate(BookTemplateMapper.toBookTemplate(request, publisher, mediaFiles));

        for (String authorId : request.getAuthorIds()) {
            Author author = authorDAO.getEntityById(Long.valueOf(authorId));
            if (author.getBookTemplates() == null) {
                author.setBookTemplates(new HashSet<>());
            }
            author.getBookTemplates().add(bookTemplate);
            if (bookTemplate.getAuthors() == null) {
                bookTemplate.setAuthors(new HashSet<>());
            }
            bookTemplate.getAuthors().add(author);

            authorDAO.updateAuthor(author);
        }

        for (String translatorId : request.getTranslatorIds()) {
            Translator translator = translatorDAO.getEntityById(Long.valueOf(translatorId));
            if (translator.getBookTemplates() == null) {
                translator.setBookTemplates(new HashSet<>());
            }
            translator.getBookTemplates().add(bookTemplate);
            if (bookTemplate.getTranslators() == null) {
                bookTemplate.setTranslators(new HashSet<>());
            }
            bookTemplate.getTranslators().add(translator);

            translatorDAO.updateTranslator(translator);
        }

        return bookTemplate;
    }

    @Override
    public BookTemplate updateBookTemplate(BookUpdateGlobalRequest request) {
        BookTemplate bookTemplate = bookTemplateDAO.findOneForDetails(Long.valueOf(request.getId()));

        // update for Publisher
        Publisher publisher = publisherDAO.getEntityById(Long.valueOf(request.getPublisherId()));
        bookTemplate.setPublisher(publisher);

        // old media file
        List<MediaFile> oldMediaFiles = bookTemplate.getMediaFiles();

        // add media file
        List<MediaFile> mediaFiles = new ArrayList<>();
        MediaFile mainImage = mediaFileService.createMediaFile(
                MediaFileCreateRequest.builder()
                        .fileName("main")
                        .storedCode(request.getMainImage())
                        .build()
        );
        mediaFiles.add(mainImage);

        for (String thumb : request.getThumbnails()) {
            mediaFiles.add(
                    mediaFileService.createMediaFile(
                            MediaFileCreateRequest.builder()
                                    .fileName("thumb")
                                    .storedCode(thumb)
                                    .build()
                    )
            );
        }
        bookTemplate.setMediaFiles(mediaFiles);

        // UPDATE
        bookTemplateDAO.updateBookTemplate(bookTemplate);

        // update for Author
        Set<Author> currentAuthors = bookTemplate.getAuthors();

        for (Author author : currentAuthors) {
            author.getBookTemplates().remove(bookTemplate);
            authorDAO.updateAuthor(author);
        }
        bookTemplate.setAuthors(new HashSet<>());

        for (String authorId : request.getAuthorIds()) {
            Author author = authorDAO.getEntityById(Long.valueOf(authorId));
            if (author.getBookTemplates() == null) {
                author.setBookTemplates(new HashSet<>());
            }
            author.getBookTemplates().add(bookTemplate);

            if (bookTemplate.getAuthors() == null) {
                bookTemplate.setAuthors(new HashSet<>());
            }
            bookTemplate.getAuthors().add(author);

            authorDAO.updateAuthor(author);
        }

        // update for Translator
        Set<Translator> currentTranslators = bookTemplate.getTranslators();

        for (Translator translator : currentTranslators) {
            translator.getBookTemplates().remove(bookTemplate);
            translatorDAO.updateTranslator(translator);
        }
        bookTemplate.setTranslators(new HashSet<>());

        for (String translatorId : request.getTranslatorIds()) {
            Translator translator = translatorDAO.getEntityById(Long.valueOf(translatorId));
            if (translator.getBookTemplates() == null) {
                translator.setBookTemplates(new HashSet<>());
            }
            translator.getBookTemplates().add(bookTemplate);

            if (bookTemplate.getTranslators() == null) {
                bookTemplate.setTranslators(new HashSet<>());
            }
            bookTemplate.getTranslators().add(translator);

            translatorDAO.updateTranslator(translator);
        }

        // delete media file
        for (MediaFile mediaFile : oldMediaFiles) {
            ManageFileUtil.deleteFileAvatar(mediaFile.getStoredCode(), "product");
        }

        return bookTemplate;
    }

    @Override
    public BookProfileResponse getBookProfileResponse(Long bookTemplateId) {
        BookTemplate bookTemplate = bookTemplateDAO.findOneForDetails(bookTemplateId);
        return BookMapper.toBookProfileResponse(bookTemplate);
    }

    @Override
    public Long getBookTemplateQuantityByCriteria(SearchBookRequest request) {
        return bookTemplateDAO.countByCriteria(request);
    }

    @Override
    public List<BookLineResponse> getAllBookLineResponse() {
        List<BookTemplate> bookTemplates = bookTemplateDAO.findAllForManagement();
        List<BookLineResponse> bookLineResponseList = new ArrayList<>();

        for (BookTemplate bookTemplate : bookTemplates) {
            Double perValueBooksSold = calculateValueBooksSoldGrowth(bookTemplate.getId());
            BookLineResponse bookLineResponse = BookTemplateMapper.toBookLineResponse(bookTemplate, perValueBooksSold);
            if (bookLineResponse != null) bookLineResponseList.add(bookLineResponse);
        }

        return bookLineResponseList;
    }

    @Override
    public BookAnalysisResponse getBookAnalysisResponse(Long bookTemplateId) {
        BookTemplate bookTemplate = bookTemplateDAO.findOneForDetails(bookTemplateId);
        Integer salesCount = bookTemplateDAO.countOrdersByStatus(bookTemplateId, EOrderStatus.COMPLETE_DELIVERY);
        Integer booksCount = bookTemplateDAO.countBooksInOrderByStatus(bookTemplateId, EBookMetadataStatus.SOLD, EOrderStatus.COMPLETE_DELIVERY);
        Long revenue = bookTemplateDAO.calculateValueBooksSold(bookTemplateId, EOrderStatus.COMPLETE_DELIVERY);
        Integer salesCountThisMonth = countSalesCountThisMonth(bookTemplateId);
        Double perSalesCountThisMonth = calculateSalesCountGrowth(bookTemplateId);
        Integer booksCountThisMonth = countBooksCountThisMonth(bookTemplateId);
        Double perBooksCountThisMonth = calculateBooksCountGrowth(bookTemplateId);
        Long revenueThisMonth = calculateRevenueThisMonth(bookTemplateId);
        Double perRevenueThisMonth = calculateValueBooksSoldGrowth(bookTemplateId);

        return BookTemplateMapper.toBookAnalysisResponse(bookTemplate, salesCount, booksCount, revenue,
                salesCountThisMonth, perSalesCountThisMonth,
                booksCountThisMonth, perBooksCountThisMonth,
                revenueThisMonth, perRevenueThisMonth);
    }

    private Double calculateSalesCountGrowth(Long id) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfThisMonth = now.withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime endOfThisMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth()).toLocalDate().atTime(23, 59, 59);

        LocalDateTime startOfLastMonth = startOfThisMonth.minusMonths(1);
        LocalDateTime endOfLastMonth = endOfThisMonth.minusMonths(1);

        Integer currentMonth = countSalesCountThisMonth(id);
        Integer lastMonth = bookTemplateDAO.countOrdersInRangeByStatus(id, startOfLastMonth, endOfLastMonth, EOrderStatus.COMPLETE_DELIVERY);

        if (lastMonth != 0) {
            return ((double) (currentMonth - lastMonth) / lastMonth) * 100.0D;
        } else {
            if (currentMonth != 0) return 100.0D;
            else return 0.0D;
        }
    }
    private Integer countSalesCountThisMonth(Long id) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfThisMonth = now.withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime endOfThisMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth()).toLocalDate().atTime(23, 59, 59);

        return bookTemplateDAO.countOrdersInRangeByStatus(id, startOfThisMonth, endOfThisMonth, EOrderStatus.COMPLETE_DELIVERY);
    }

    private Double calculateBooksCountGrowth(Long id) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfThisMonth = now.withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime endOfThisMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth()).toLocalDate().atTime(23, 59, 59);

        LocalDateTime startOfLastMonth = startOfThisMonth.minusMonths(1);
        LocalDateTime endOfLastMonth = endOfThisMonth.minusMonths(1);

        Integer currentMonth = countBooksCountThisMonth(id);
        Integer lastMonth = bookTemplateDAO.countBooksInRangeByStatus(id, startOfLastMonth, endOfLastMonth, EBookMetadataStatus.SOLD, EOrderStatus.COMPLETE_DELIVERY);

        if (lastMonth != 0) {
            return ((double) (currentMonth - lastMonth) / lastMonth) * 100.0D;
        } else {
            if (currentMonth != 0) return 100.0D;
            else return 0.0D;
        }
    }
    private Integer countBooksCountThisMonth(Long id) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfThisMonth = now.withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime endOfThisMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth()).toLocalDate().atTime(23, 59, 59);

        return bookTemplateDAO.countBooksInRangeByStatus(id, startOfThisMonth, endOfThisMonth, EBookMetadataStatus.SOLD, EOrderStatus.COMPLETE_DELIVERY);
    }

    private Double calculateValueBooksSoldGrowth(Long id) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfThisMonth = now.withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime endOfThisMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth()).toLocalDate().atTime(23, 59, 59);

        LocalDateTime startOfLastMonth = startOfThisMonth.minusMonths(1);
        LocalDateTime endOfLastMonth = endOfThisMonth.minusMonths(1);

        Long currentMonth = calculateRevenueThisMonth(id);
        Long lastMonth = bookTemplateDAO.calculateValueBooksSoldInRange(id, startOfLastMonth, endOfLastMonth, EOrderStatus.COMPLETE_DELIVERY);

        if (lastMonth != 0) {
            return ((double) (currentMonth - lastMonth) / lastMonth) * 100.0D;
        } else {
            if (currentMonth != 0) return 100.0D;
            else return 0.0D;
        }
    }
    private Long calculateRevenueThisMonth(Long id) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfThisMonth = now.withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime endOfThisMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth()).toLocalDate().atTime(23, 59, 59);

        return bookTemplateDAO.calculateValueBooksSoldInRange(id, startOfThisMonth, endOfThisMonth, EOrderStatus.COMPLETE_DELIVERY);
    }

    public CheckoutItemResponse getCheckoutItemResponse(CheckoutItemRequest request) {
        BookTemplate bookTemplate = bookTemplateDAO.findOneForDetails(request.getProductId());
        Book book = bookTemplate.getBooks().iterator().next();
        return CheckoutItemResponse.builder().bookTemplateId(bookTemplate.getId()).title(book.getTitle()).imagePath(bookTemplate.getMediaFiles().get(0).getStoredCode()).quantity(request.getQuantity()).sellingPrice(book.getSellingPrice()).build();
    }

    @Override
    public boolean verifyBookTemplateQuantity(Long bookTemplateId) {
        Long quantity = bookTemplateDAO.countInstockById(bookTemplateId);
        BookTemplate bookTemplate = bookTemplateDAO.findById(bookTemplateId);
        if (bookTemplate == null) {
            return false;
        }
        if (quantity > 0 && bookTemplate.getStatus().equals(EBookTemplateStatus.OUT_OF_STOCK)) {
            bookTemplate.setStatus(EBookTemplateStatus.ON_SALE);
            return bookTemplateDAO.update(bookTemplate) != null;
        }
        if (quantity == 0 && bookTemplate.getStatus().equals(EBookTemplateStatus.ON_SALE)) {
            bookTemplate.setStatus(EBookTemplateStatus.OUT_OF_STOCK);
            return bookTemplateDAO.update(bookTemplate) != null;
        }
        return true;

    }

    @Override
    public List<BookCardResponse> getPopularBookCard() {
        List<BookTemplate> bookTemplates = bookTemplateDAO.findTop20();
        List<BookCardResponse> bookCardResponseList = new ArrayList<>();
        for (BookTemplate bookTemplate : bookTemplates) {
            bookCardResponseList.add(BookTemplateMapper.toBookCardResponse(bookTemplate));
        }
        return bookCardResponseList;
    }

    @Override
    public List<BookCardResponse> getRelatedBooks(LoadRelatedBooksRequest request) {
        List<BookTemplate> bookTemplates = bookTemplateDAO.findRelatedBooks(request);
        List<BookCardResponse> bookCardResponseList = new ArrayList<>();
        for (BookTemplate bookTemplate : bookTemplates) {
            bookCardResponseList.add(BookTemplateMapper.toBookCardResponse(bookTemplate));
        }
        return bookCardResponseList;
    }
}
