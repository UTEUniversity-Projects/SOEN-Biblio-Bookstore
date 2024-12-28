package com.biblio.service;

import com.biblio.dto.request.BookCreateGlobalRequest;
import com.biblio.dto.request.BookUpdateGlobalRequest;
import com.biblio.dto.request.SearchBookRequest;
import com.biblio.dto.response.*;
import com.biblio.entity.BookTemplate;
import com.biblio.dto.request.CheckoutItemRequest;
import com.biblio.dto.request.LoadRelatedBooksRequest;
import com.biblio.dto.response.*;

import java.util.List;

public interface IBookTemplateService {

    List<BookManagementResponse> getAllBookManagementResponse();

    List<BookCardResponse> getAllBookCardResponse();

    List<BookCardResponse> getPopularBookCard();

    BookDetailsResponse getBookDetailsResponse(Long bookTemplateId);

    List<BookTemplatePromotionResponse> getAllBookBookTemplatePromotionResponse();

    List<BookCardResponse> getBookTemplateByCriteria(SearchBookRequest request);

    Long getBookTemplateQuantityByCriteria(SearchBookRequest request);
  
    List<BookSoldAllTimeResponse> getListCountBookSoldAllTime();

    List<BookLineResponse> getAllBookLineResponse();

    BookAnalysisResponse getBookAnalysisResponse(Long bookTemplateId);

    Long getTotalBookTemplateQuantity();

    BookCreateResponse initCreateBook();

    BookUpdateResponse initUpdateBook();

    BookTemplate createBookTemplate(BookCreateGlobalRequest request);

    BookTemplate updateBookTemplate(BookUpdateGlobalRequest request);

    BookProfileResponse getBookProfileResponse(Long bookTemplateId);

    CheckoutItemResponse getCheckoutItemResponse(CheckoutItemRequest request);

    boolean verifyBookTemplateQuantity(Long bookTemplateId);

    List<BookCardResponse> getRelatedBooks(LoadRelatedBooksRequest request);

}
