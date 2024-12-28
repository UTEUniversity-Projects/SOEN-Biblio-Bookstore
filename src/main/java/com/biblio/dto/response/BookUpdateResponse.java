package com.biblio.dto.response;

import com.biblio.entity.*;
import com.biblio.utils.PairUtil;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookUpdateResponse {
    List<Category> categories;
    List<Author> authors;
    List<Translator> translators;
    List<Publisher> publishers;
    List<PairUtil> conditions;
    List<PairUtil> formats;
    List<PairUtil> languages;
    List<PairUtil> ageRecommends;
}
