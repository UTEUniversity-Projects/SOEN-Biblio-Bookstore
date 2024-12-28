package com.biblio.dao.impl;

import com.biblio.dao.IMediaFileDAO;
import com.biblio.entity.MediaFile;

import java.util.HashMap;
import java.util.Map;

public class MediaFileDAOImpl extends GenericDAOImpl<MediaFile> implements IMediaFileDAO {

    public MediaFileDAOImpl() {
        super(MediaFile.class);
    }

    @Override
    public MediaFile createMediaFile(MediaFile mediaFile) {
        return super.insert(mediaFile);
    }
}
