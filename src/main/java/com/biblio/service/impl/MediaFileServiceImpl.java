package com.biblio.service.impl;

import com.biblio.dao.IMediaFileDAO;
import com.biblio.dto.request.MediaFileCreateRequest;
import com.biblio.entity.MediaFile;
import com.biblio.mapper.MediaFileMapper;
import com.biblio.service.IMediaFileService;

import javax.inject.Inject;

public class MediaFileServiceImpl implements IMediaFileService {

    @Inject
    IMediaFileDAO mediaFileDAO;

    @Override
    public MediaFile createMediaFile(MediaFileCreateRequest mediaFileCreateRequest) {
        return mediaFileDAO.createMediaFile(MediaFileMapper.toMediaFile(mediaFileCreateRequest));
    }
}
