package com.biblio.mapper;

import com.biblio.dao.impl.BookTemplateDAOImpl;
import com.biblio.dto.request.BookCreateGlobalRequest;
import com.biblio.dto.request.BookRequest;
import com.biblio.dto.request.BookUpdateGlobalRequest;
import com.biblio.dto.response.BookProfileResponse;
import com.biblio.dto.response.BookResponse;
import com.biblio.dto.response.BookSoldResponse;
import com.biblio.dto.response.CountBookSoldResponse;
import com.biblio.entity.*;
import com.biblio.enumeration.EBookAgeRecommend;
import com.biblio.enumeration.EBookCondition;
import com.biblio.enumeration.EBookFormat;
import com.biblio.enumeration.EBookMetadataStatus;
import com.biblio.utils.EnumUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BookMapper {

    public static Book toBookEntity(
            BookCreateGlobalRequest bookCreateRequest,
            BookTemplate bookTemplate,
            SubCategory subCategory,
            BookMetadata bookMetadata
    ) {
        return Book.builder()
                .title(bookCreateRequest.getTitle())
                .description(bookCreateRequest.getDescription())
                .sellingPrice(Double.parseDouble(bookCreateRequest.getSellingPrice()))
                .publicationDate(LocalDateTime.parse(bookCreateRequest.getPublicationDate() + "T00:00:00"))
                .edition(Integer.parseInt(bookCreateRequest.getEdition()))
                .codeISBN10(bookCreateRequest.getCodeISBN10())
                .codeISBN13(bookCreateRequest.getCodeISBN13())
                .format(EBookFormat.valueOf(bookCreateRequest.getFormatCode()))
                .handcover(Integer.parseInt(bookCreateRequest.getHardcover()))
                .length(Double.parseDouble(bookCreateRequest.getLength()))
                .width(Double.parseDouble(bookCreateRequest.getWidth()))
                .height(Double.parseDouble(bookCreateRequest.getHeight()))
                .weight(Double.parseDouble(bookCreateRequest.getWeight()))
                .condition(EBookCondition.valueOf(bookCreateRequest.getConditionCode()))
                .recommendedAge(EBookAgeRecommend.valueOf(bookCreateRequest.getAgeRecommendCode()))
                .bookMetadata(bookMetadata)
                .bookTemplate(bookTemplate)
                .subCategory(subCategory)
                .build();
    }

    public static Book toBookEntity(
            BookUpdateGlobalRequest bookUpdateRequest,
            BookTemplate bookTemplate,
            SubCategory subCategory,
            BookMetadata bookMetadata,
            Book book
    ) {
        return Book.builder()
                .id(book.getId())
                .title(bookUpdateRequest.getTitle())
                .description(bookUpdateRequest.getDescription())
                .sellingPrice(Double.parseDouble(bookUpdateRequest.getSellingPrice()))
                .publicationDate(LocalDateTime.parse(bookUpdateRequest.getPublicationDate() + "T00:00:00"))
                .edition(Integer.parseInt(bookUpdateRequest.getEdition()))
                .codeISBN10(bookUpdateRequest.getCodeISBN10())
                .codeISBN13(bookUpdateRequest.getCodeISBN13())
                .format(EBookFormat.valueOf(bookUpdateRequest.getFormatCode()))
                .handcover(Integer.parseInt(bookUpdateRequest.getHardcover()))
                .length(Double.parseDouble(bookUpdateRequest.getLength()))
                .width(Double.parseDouble(bookUpdateRequest.getWidth()))
                .height(Double.parseDouble(bookUpdateRequest.getHeight()))
                .weight(Double.parseDouble(bookUpdateRequest.getWeight()))
                .condition(EBookCondition.valueOf(bookUpdateRequest.getConditionCode()))
                .recommendedAge(EBookAgeRecommend.valueOf(bookUpdateRequest.getAgeRecommendCode()))
                .bookMetadata(bookMetadata)
                .bookTemplate(bookTemplate)
                .subCategory(subCategory)
                .build();
    }

    public static Book toBookEntity(
            BookUpdateGlobalRequest bookUpdateRequest,
            BookTemplate bookTemplate,
            SubCategory subCategory,
            BookMetadata bookMetadata
    ) {
        return Book.builder()
                .title(bookUpdateRequest.getTitle())
                .description(bookUpdateRequest.getDescription())
                .sellingPrice(Double.parseDouble(bookUpdateRequest.getSellingPrice()))
                .publicationDate(LocalDateTime.parse(bookUpdateRequest.getPublicationDate() + "T00:00:00"))
                .edition(Integer.parseInt(bookUpdateRequest.getEdition()))
                .codeISBN10(bookUpdateRequest.getCodeISBN10())
                .codeISBN13(bookUpdateRequest.getCodeISBN13())
                .format(EBookFormat.valueOf(bookUpdateRequest.getFormatCode()))
                .handcover(Integer.parseInt(bookUpdateRequest.getHardcover()))
                .length(Double.parseDouble(bookUpdateRequest.getLength()))
                .width(Double.parseDouble(bookUpdateRequest.getWidth()))
                .height(Double.parseDouble(bookUpdateRequest.getHeight()))
                .weight(Double.parseDouble(bookUpdateRequest.getWeight()))
                .condition(EBookCondition.valueOf(bookUpdateRequest.getConditionCode()))
                .recommendedAge(EBookAgeRecommend.valueOf(bookUpdateRequest.getAgeRecommendCode()))
                .bookMetadata(bookMetadata)
                .bookTemplate(bookTemplate)
                .subCategory(subCategory)
                .build();
    }

    public static BookProfileResponse toBookProfileResponse(BookTemplate bookTemplate) {
        Book singleBook = bookTemplate.getBooks().iterator().next();

        return BookProfileResponse.builder()
                .id(bookTemplate.getId())
                .title(singleBook.getTitle())
                .sellingPrice((long) singleBook.getSellingPrice())
                .importPrice((long) singleBook.getSellingPrice())
                .description(singleBook.getDescription())
                .quantity((int) bookTemplate.getBooks().stream().
                        filter(book -> book.getBookMetadata().getStatus() == EBookMetadataStatus.IN_STOCK).count())
                .authorIds(bookTemplate.getAuthors().stream()
                        .map(ContributorProfile::getId).collect(Collectors.toList()))
                .translatorIds(bookTemplate.getTranslators().stream()
                        .map(ContributorProfile::getId).collect(Collectors.toList()))
                .subCategoryId(singleBook.getSubCategory().getId())
                .conditionCode(singleBook.getCondition().name())
                .publisherId(bookTemplate.getPublisher().getId())
                .formatCode(singleBook.getFormat().name())
                .publicationDate(singleBook.getPublicationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .languageCodes(bookTemplate.getLanguages().stream()
                        .map(Enum::name).collect(Collectors.toList()))
                .ageRecommendCode(singleBook.getRecommendedAge().name())
                .codeISBN10(singleBook.getCodeISBN10())
                .codeISBN13(singleBook.getCodeISBN13())
                .edition(singleBook.getEdition())
                .hardcover(singleBook.getHandcover())
                .length(singleBook.getLength())
                .width(singleBook.getWidth())
                .height(singleBook.getHeight())
                .weight(singleBook.getWeight())
                .description(singleBook.getDescription())
                .imgUrls(bookTemplate.getMediaFiles().stream()
                        .sorted(Comparator.comparing(MediaFile::getId))
                        .map(MediaFile::getStoredCode)
                        .toList())
                .build();
    }

    // region EntityToDTO

    public static BookResponse toBookResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId().toString())
                .title(book.getTitle())
                .description(book.getDescription())
                .sellingPrice(Double.toString(book.getSellingPrice()))
                .publicationDate(book.getPublicationDate().toString())
                .edition(Integer.toString(book.getEdition()))
                .codeISBN10(book.getCodeISBN10())
                .codeISBN13(book.getCodeISBN13())
                .format(EnumUtil.toDisplayName(book.getFormat()))
                .handCover(Integer.toString(book.getHandcover()))
                .length(Double.toString(book.getLength()))
                .width(Double.toString(book.getWidth()))
                .height(Double.toString(book.getHeight()))
                .weight(Double.toString(book.getWeight()))
                .condition(EnumUtil.toDisplayName(book.getCondition()))
                .recommendedAge(EnumUtil.toDisplayName(book.getRecommendedAge()))
                .languages(book.getBookTemplate().getLanguages())
                .build();
    }

    // endregion

    // region DTOtoEntity

    public static Book toBookEntity(BookRequest bookRequest) {
        return Book.builder()
                .id(Long.parseLong(bookRequest.getId()))
                .title(bookRequest.getTitle())
                .description(bookRequest.getDescription())
                .sellingPrice(Double.parseDouble(bookRequest.getSellingPrice()))
                .publicationDate(LocalDateTime.parse(bookRequest.getPublicationDate()))
                .edition(Integer.parseInt(bookRequest.getEdition()))
                .codeISBN10(bookRequest.getCodeISBN10())
                .codeISBN13(bookRequest.getCodeISBN13())
                .format(EBookFormat.valueOf(bookRequest.getFormat()))
                .handcover(Integer.parseInt(bookRequest.getHandCover()))
                .length(Double.parseDouble(bookRequest.getLength()))
                .width(Double.parseDouble(bookRequest.getWidth()))
                .height(Double.parseDouble(bookRequest.getHeight()))
                .weight(Double.parseDouble(bookRequest.getWeight()))
                .condition(EBookCondition.valueOf(bookRequest.getCondition()))
                .recommendedAge(EBookAgeRecommend.valueOf(bookRequest.getRecommendedAge()))
                .build();
    }

    public static BookSoldResponse toBookSoldResponse(Book book) {
        BookTemplateDAOImpl bookTemplateDAO = new BookTemplateDAOImpl();
        return BookSoldResponse.builder()
                .id(book.getBookTemplate().getId())
                .title(book.getTitle())
                .srcImg(book.getBookTemplate()
                        .getMediaFiles()
                        .get(0)
                        .getStoredCode())
                .category(book.getSubCategory().getCategory().getName())
                .countInStock(bookTemplateDAO.countInstockById(book.getBookTemplate().getId()))// or provide a default value like "" if you prefer
                .build();
    }
    public static List<CountBookSoldResponse> toCountBookSoldResponse (List<BookSoldResponse> bookSoldResponses) {
        Map<String, Long> countMap = bookSoldResponses.stream()
                .collect(Collectors.groupingBy(book -> book.getId() + "-" + book.getSrcImg() + "-" + book.getTitle() + "-" + book.getCategory() + "-" + book.getCountInStock(), Collectors.counting()));

        return countMap.entrySet().stream()
                .map(entry -> {
                    String[] parts = entry.getKey().split("-");
                    return new CountBookSoldResponse(
                            Long.parseLong(parts[0]),      // id
                            parts[1],                     // srcImg
                            parts[2],                     // title
                            parts[3],                     // category
                            Long.parseLong(parts[4]),     // countInStock
                            entry.getValue()              // count
                    );
                })
                .collect(Collectors.toList());
    }
    // endregion
}
