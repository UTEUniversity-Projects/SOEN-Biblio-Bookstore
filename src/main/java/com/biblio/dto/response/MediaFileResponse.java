package com.biblio.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MediaFileResponse {
    private String fileName;
    private String storedCode;
}
