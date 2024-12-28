package com.biblio.dto.response;

import com.biblio.enumeration.EBookTemplateStatus;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class BookManagementResponse {
    long id;
    String imageUrl;
    String title;
    double price;
    long quantity;
    long soldCount;
    private String publicationDate;
    private EBookTemplateStatus status;
    private String statusStyle;
}
