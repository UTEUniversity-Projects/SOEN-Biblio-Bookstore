package com.biblio.service;

import com.biblio.dto.request.MediaFileCreateRequest;
import com.biblio.entity.MediaFile;

public interface IMediaFileService {
    MediaFile createMediaFile(MediaFileCreateRequest mediaFileCreateRequest);
}
