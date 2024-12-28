package com.biblio.service;

import com.biblio.dto.request.TagRequest;
import com.biblio.dto.response.TagResponse;

import java.util.List;

public interface ITagService {
    List<TagResponse> findAll();
    TagResponse findById(Long id);
    void addTag(TagRequest tagRequest);
    void updateTag(TagRequest tagRequest);
    void deleteTag(Long id);
}
