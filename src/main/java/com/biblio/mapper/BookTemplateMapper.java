package com.biblio.mapper;

import com.biblio.dao.impl.BookTemplateDAOImpl;
import com.biblio.dto.request.BookCreateGlobalRequest;
import com.biblio.dto.request.BookUpdateGlobalRequest;
import com.biblio.dto.response.*;
import com.biblio.entity.*;
import com.biblio.enumeration.*;
import com.biblio.utils.EnumUtil;
import com.biblio.utils.FormatterUtil;
import com.biblio.enumeration.EBookLanguage;
import com.biblio.enumeration.EBookMetadataStatus;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.biblio.utils.DateTimeUtil.formatDateTime;

public class BookTemplateMapper {

    public static BookLineResponse toBookLineResponse(
            BookTemplate bookTemplate,
            Double perValueBooksSold
    ) {
        if (bookTemplate.getBooks().iterator().next() == null) {
            return null;
        }
        Book singleBook = bookTemplate.getBooks().iterator().next();
        Double avgRate = bookTemplate.calculateReviewRate();

        return BookLineResponse.builder()
                .id(bookTemplate.getId())
                .imageUrl(bookTemplate
                        .getMediaFiles()
                        .get(0)
                        .getStoredCode())
                .title(singleBook.getTitle())
                .publisher(bookTemplate.getPublisher().getName())
                .perValueBooksSold(perValueBooksSold != null ? Double.parseDouble(FormatterUtil.percent(perValueBooksSold)) : 0.0D)
                .booksSold(String.valueOf(bookTemplate.getBooks().stream()
                        .filter(book -> book.getBookMetadata().getStatus() == EBookMetadataStatus.SOLD).count()))
                .avgRate(FormatterUtil.rating(avgRate))
                .sellingPrice(FormatterUtil.commaNumber((long) singleBook.getSellingPrice()))
                .statusStyle(bookTemplate.getStatus().getStatusStyle())
                .statusDisplay(bookTemplate.getStatus().getDescription())
                .build();
    }

    public static BookAnalysisResponse toBookAnalysisResponse(
            BookTemplate bookTemplate,
            Integer salesCount,
            Integer booksCount,
            Long revenue,
            Integer salesCountThisMonth,
            Double perSalesCountThisMonth,
            Integer booksCountThisMonth,
            Double perBooksCountThisMonth,
            Long revenueThisMonth,
            Double perRevenueThisMonth
    ) {
        Book singleBook = bookTemplate.getBooks().iterator().next();

        String languages = bookTemplate.getLanguages() != null
                ? bookTemplate.getLanguages().stream()
                .map(EBookLanguage::getDescription)
                .collect(Collectors.joining(", ")) : "N/A";

        List<String> fileNames = bookTemplate.getMediaFiles().stream()
                .sorted(Comparator.comparing(MediaFile::getId))
                .map(MediaFile::getStoredCode)
                .toList();

        List<AuthorProfileResponse> authors = new ArrayList<>();
        for (Author author : bookTemplate.getAuthors()) {
            authors.add(AuthorMapper.toAuthorProfileResponse(author));
        }

        List<TranslatorProfileResponse> translators = new ArrayList<>();
        for (Translator translator : bookTemplate.getTranslators()) {
            translators.add(TranslatorMapper.toTranslatorProfileResponse(translator));
        }

        List<ReviewResponse> reviews = bookTemplate.getReviews().stream()
                .filter(review -> !review.isHidden())
                .sorted(Comparator.comparingInt(Review::getRate).reversed()
                        .thenComparing(Review::getCreatedAt, Comparator.reverseOrder()))
                .map(ReviewMapper::toReviewResponse)
                .toList();

        return BookAnalysisResponse.builder()
                .id(bookTemplate.getId())
                .title(singleBook.getTitle())
                .avgRating(Double.parseDouble(FormatterUtil.rating(bookTemplate.calculateReviewRate())))
                .reviewCount(reviews.size())
                .format(singleBook.getFormat().getBookFormat())
                .sellingPrice(FormatterUtil.commaNumber((long) singleBook.getSellingPrice()))
                .originPrice(FormatterUtil.commaNumber((long) ((long) singleBook.getSellingPrice() * 1.2)))
                .condition(singleBook.getCondition().getBookCondition())
                .hardcover(singleBook.getHandcover())
                .publisher(bookTemplate.getPublisher().getName())
                .publicationDate(FormatterUtil.toDateTimeString(singleBook.getPublicationDate()))
                .size(String.format("%.1f x %.1f x %.1f cm", singleBook.getHeight(), singleBook.getLength(), singleBook.getWidth()))
                .weight(singleBook.getWeight())
                .languages(languages)
                .codeISBN10(singleBook.getCodeISBN10())
                .codeISBN13(singleBook.getCodeISBN13())
                .description(singleBook.getDescription())
                .category(singleBook.getSubCategory().getCategory().getName())
                .edition(singleBook.getEdition())
                .recommendedAge(singleBook.getRecommendedAge().getBookAgeRecommend())
                .quantity(bookTemplate.getBooks().stream().
                        filter(book -> book.getBookMetadata().getStatus() == EBookMetadataStatus.IN_STOCK).count())
                .salesCount(FormatterUtil.dotNumber(Long.valueOf(salesCount)))
                .booksCount(FormatterUtil.dotNumber(Long.valueOf(booksCount)))
                .revenue(FormatterUtil.commaNumber(revenue))
                .salesCountThisMonth(FormatterUtil.dotNumber(Long.valueOf(salesCountThisMonth)))
                .perSalesCountThisMonth(Double.parseDouble(FormatterUtil.percent(perSalesCountThisMonth)))
                .booksCountThisMonth(FormatterUtil.dotNumber(Long.valueOf(booksCountThisMonth)))
                .perBooksCountThisMonth(Double.parseDouble(FormatterUtil.percent(perBooksCountThisMonth)))
                .revenueThisMonth(FormatterUtil.commaNumber(revenueThisMonth))
                .perRevenueThisMonth(Double.parseDouble(FormatterUtil.percent(perRevenueThisMonth)))
                .imageUrls(fileNames)
                .authors(authors)
                .translators(translators)
                .reviews(reviews)
                .build();
    }

    public static BookCreateResponse toBookCreateResponse(
            List<Category> categories,
            List<Author> authors,
            List<Translator> translators,
            List<Publisher> publishers
    ) {
        return BookCreateResponse.builder()
                .categories(categories)
                .authors(authors)
                .translators(translators)
                .publishers(publishers)
                .conditions(EnumUtil.mapEnumToPairUtil(EBookCondition.class))
                .formats(EnumUtil.mapEnumToPairUtil(EBookFormat.class))
                .languages(EnumUtil.mapEnumToPairUtil(EBookLanguage.class))
                .ageRecommends(EnumUtil.mapEnumToPairUtil(EBookAgeRecommend.class))
                .build();
    }

    public static BookUpdateResponse toBookUpdateResponse(
            List<Category> categories,
            List<Author> authors,
            List<Translator> translators,
            List<Publisher> publishers
    ) {
        return BookUpdateResponse.builder()
                .categories(categories)
                .authors(authors)
                .translators(translators)
                .publishers(publishers)
                .conditions(EnumUtil.mapEnumToPairUtil(EBookCondition.class))
                .formats(EnumUtil.mapEnumToPairUtil(EBookFormat.class))
                .languages(EnumUtil.mapEnumToPairUtil(EBookLanguage.class))
                .ageRecommends(EnumUtil.mapEnumToPairUtil(EBookAgeRecommend.class))
                .build();
    }

    public static BookTemplate toBookTemplate(
            BookCreateGlobalRequest request,
            Publisher publisher,
            List<MediaFile> mediaFiles
    ) {
        Set<EBookLanguage> languages = new HashSet<>();
        for (String language : request.getLanguageCodes()) {
            languages.add(EBookLanguage.valueOf(language));
        }

        return BookTemplate.builder()
                .status(EBookTemplateStatus.ON_SALE)
                .languages(languages)
                .publisher(publisher)
                .mediaFiles(mediaFiles)
                .build();
    }

    public static BookTemplate toBookTemplate(
            BookUpdateGlobalRequest request,
            Publisher publisher,
            List<MediaFile> mediaFiles
    ) {
        Set<EBookLanguage> languages = new HashSet<>();
        for (String language : request.getLanguageCodes()) {
            languages.add(EBookLanguage.valueOf(language));
        }

        return BookTemplate.builder()
                .status(EBookTemplateStatus.ON_SALE)
                .languages(languages)
                .publisher(publisher)
                .mediaFiles(mediaFiles)
                .build();
    }

    // region Entity to DTO

    public static BookManagementResponse toBookManagementResponse(BookTemplate bookTemplate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
        Book singlebook = bookTemplate.getBooks().iterator().next();
        return BookManagementResponse.builder()
                .id(bookTemplate.getId())
                .imageUrl(bookTemplate
                        .getMediaFiles()
                        .get(0)
                        .getStoredCode())
                .title(singlebook.getTitle())
                .price(singlebook.getSellingPrice())
                .quantity(bookTemplate.getBooks().stream().
                        filter(book -> book.getBookMetadata().getStatus() == EBookMetadataStatus.IN_STOCK).count())
                .soldCount(bookTemplate.getBooks().stream().
                        filter(book -> book.getBookMetadata().getStatus() == EBookMetadataStatus.SOLD).count())
                .publicationDate(singlebook.getPublicationDate().format(formatter))
                .status(bookTemplate.getStatus())
                .statusStyle(bookTemplate.getStatus().getStatusStyle())
                .build();
    }

    public static BookCardResponse toBookCardResponse(BookTemplate bookTemplate) {

        DecimalFormat formatter = new DecimalFormat("#.###");

        Book singlebook = bookTemplate.getBooks().iterator().next();
        return BookCardResponse.builder()
                .id(bookTemplate.getId())
                .title(singlebook.getTitle())
                .sellingPrice(singlebook.getSellingPrice())
                .condition(singlebook.getCondition().getBookCondition())
                .categoryName(singlebook.getSubCategory().getCategory().getName())
                .imageUrl(bookTemplate
                        .getMediaFiles()
                        .get(0)
                        .getStoredCode())
                .reviewRate(bookTemplate.calculateReviewRate())
                .numberOfReviews(bookTemplate.getReviews().stream().filter(review -> !review.isHidden()).count())
                .build();
    }

    public static BookDetailsResponse toBookDetailsResponse(BookTemplate bookTemplate) {
        Book singlebook = bookTemplate.getBooks().iterator().next();

        String languages = bookTemplate.getLanguages() != null
                ? bookTemplate.getLanguages().stream()
                .map(EBookLanguage::getDescription)
                .collect(Collectors.joining(", ")) : "N/A";

        List<String> fileNames = bookTemplate.getMediaFiles().stream()
                .sorted(Comparator.comparing(MediaFile::getId))
                .map(MediaFile::getStoredCode)
                .toList();

        List<AuthorProfileResponse> authors = new ArrayList<>();
        for (Author author : bookTemplate.getAuthors()) {
            authors.add(AuthorMapper.toAuthorProfileResponse(author));
        }

        List<TranslatorProfileResponse> translators = new ArrayList<>();
        for (Translator translator : bookTemplate.getTranslators()) {
            translators.add(TranslatorMapper.toTranslatorProfileResponse(translator));
        }

        List<ReviewResponse> reviews = bookTemplate.getReviews().stream()
                .sorted(Comparator.comparingInt((Review review) -> review.isHidden() ? 1 : 0)
                        .thenComparing(Comparator.comparingInt(Review::getRate).reversed())
                        .thenComparing(Review::getCreatedAt, Comparator.reverseOrder()))
                .map(ReviewMapper::toReviewResponse)
                .toList();

        return BookDetailsResponse.builder()
                .id(bookTemplate.getId())
                .title(singlebook.getTitle())
                .description(singlebook.getDescription())
                .sellingPrice(singlebook.getSellingPrice())
                .publicationDate(formatDateTime(singlebook.getPublicationDate(), "dd-MM-yyyy"))
                .edition(singlebook.getEdition())
                .codeISBN10(singlebook.getCodeISBN10())
                .codeISBN13(singlebook.getCodeISBN13())
                .format(singlebook.getFormat().getBookFormat())
                .hardcover(singlebook.getHandcover())
                .size(String.format("%.1f x %.1f x %.1f cm",
                        singlebook.getHeight(), singlebook.getLength(), singlebook.getWidth()))
                .weight(singlebook.getWeight())
                .condition(singlebook.getCondition().getBookCondition())
                .recommendedAge(singlebook.getRecommendedAge().getBookAgeRecommend())
                .category(singlebook.getSubCategory().getCategory().getName())
                .languages(languages)
                .quantity(bookTemplate.getBooks().stream().
                        filter(book -> book.getBookMetadata().getStatus() == EBookMetadataStatus.IN_STOCK).count())
                .avgRating(bookTemplate.calculateReviewRate())
                .imageUrls(fileNames)
                .publisher(bookTemplate.getPublisher().getName())
                .authors(authors)
                .translators(translators)
                .reviews(reviews)
                .reviewCount(reviews.size())
                .build();
    }

    public static BookTemplatePromotionResponse toBookTemplatePromotionResponse(BookTemplate bookTemplate) {
        Book singlebook = bookTemplate.getBooks().iterator().next();
        return BookTemplatePromotionResponse.builder()
                .id(bookTemplate.getId())
                .title(singlebook.getTitle())
                .subCategoryId(singlebook.getSubCategory().getId())
                .build();
    }
    public static BookSoldAllTimeResponse toBookSoldAllTimeResponse(BookTemplate bookTemplate) {
        BookTemplateDAOImpl bookTemplateDAO = new BookTemplateDAOImpl();
        Book singlebook = bookTemplate.getBooks().iterator().next();
        return BookSoldAllTimeResponse.builder()
                .id(bookTemplate.getId())
                .srcImg(bookTemplate.getMediaFiles()
                        .get(0)
                        .getStoredCode())
                .title(singlebook.getTitle())
                .category(singlebook.getSubCategory().getCategory().getName())
                .countSold(bookTemplateDAO.countSoldById(bookTemplate.getId()))
                .countInStock(bookTemplateDAO.countInstockById(bookTemplate.getId()))
                .build();
    }

    // endregion

    // region DTO to Entity

    // endregion
}
