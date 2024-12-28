package com.biblio.mapper;

import com.biblio.dto.request.BookCreateGlobalRequest;
import com.biblio.dto.request.MediaFileCreateRequest;
import com.biblio.dto.response.MediaFileResponse;
import com.biblio.entity.MediaFile;

public class MediaFileMapper {

    public static MediaFile toMediaFile(MediaFileCreateRequest mediaFileCreateRequest) {
        return MediaFile.builder()
                .fileName(mediaFileCreateRequest.getFileName())
                .storedCode(mediaFileCreateRequest.getStoredCode())
                .build();
    }

    public static MediaFileResponse toMediaFileResponse(MediaFile mediaFile) {
        MediaFileResponse mediaFileResponse = new MediaFileResponse();
        mediaFileResponse.setFileName(mediaFile.getFileName());
        mediaFileResponse.setStoredCode(mediaFile.getStoredCode());

        return mediaFileResponse;
    }
}
