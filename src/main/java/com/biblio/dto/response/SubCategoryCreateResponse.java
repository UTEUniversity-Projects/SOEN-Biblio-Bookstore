package com.biblio.dto.response;

import com.biblio.entity.Category;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubCategoryCreateResponse {
    List<Category> categories;
}
