package com.biblio.dao;

import com.biblio.dto.request.TranslatorCreateRequest;
import com.biblio.dto.request.TranslatorDeleteRequest;
import com.biblio.dto.request.TranslatorUpdateRequest;
import com.biblio.entity.Translator;
import com.biblio.entity.BookTemplate;
import com.biblio.enumeration.EBookMetadataStatus;
import com.biblio.enumeration.EBookTemplateStatus;
import com.biblio.enumeration.EOrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface ITranslatorDAO {
    Translator getEntityById(Long translatorId);

    List<Translator> getEntityAll();

    List<Translator> findByBookTemplate(BookTemplate bookTemplate);

    List<String> getTopSubCategory(Long translatorId);

    Translator createTranslator(Translator translator);

    void updateTranslator(Translator translator);

    void deleteTranslator(Long id);

    Integer countBooksTemplateAll(Long translatorId);

    Integer countBooksTemplateByStatus(Long translatorId, EBookTemplateStatus status);

    Integer countBooksByStatus(Long translatorId, EBookMetadataStatus status);

    Integer countBooksByOrderStatus(Long translatorId, EOrderStatus orderStatus);

    Integer countBooksInRangeByStatus(Long translatorId, LocalDateTime from, LocalDateTime to, EBookMetadataStatus bookStatus, EOrderStatus orderStatus);

    Integer countOrdersAll(Long translatorId);

    Integer countOrdersByStatus(Long translatorId, EOrderStatus status);

    Integer countOrdersInRangeByStatus(Long translatorId, LocalDateTime from, LocalDateTime to, EOrderStatus status);

    Long calculateValueBooksSold(Long translatorId);

    Long calculateValueBooksSoldInRange(Long translatorId, LocalDateTime from, LocalDateTime to);

    Long calculateValueOrdersSoldByStatus(Long translatorId, EOrderStatus status);

    Double calculateAverageRating(Long translatorId);
}
