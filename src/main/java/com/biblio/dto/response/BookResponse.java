package com.biblio.dto.response;

import com.biblio.enumeration.EBookLanguage;
import java.util.HashSet;
import java.util.Set;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookResponse {
    String id;
    String title;
    String description;
    String sellingPrice;
    String publicationDate;
    String edition;
    String codeISBN10;
    String codeISBN13;
    String format;
    String handCover;
    String length;
    String width;
    String height;
    String weight;
    String condition;
    String recommendedAge;
    Set<EBookLanguage> languages = new HashSet<>();
}
