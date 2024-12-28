package com.biblio.dto.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BookMetadataResponse {
    private List<MediaFileResponse> mediaFiles = new ArrayList<>() ;
}
