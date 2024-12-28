package com.biblio.service.impl;

import com.biblio.dao.ITagDAO;
import com.biblio.dto.request.TagRequest;
import com.biblio.dto.response.TagResponse;
import com.biblio.entity.Tag;
import com.biblio.mapper.TagMapper;
import com.biblio.service.ITagService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class TagServiceImpl implements ITagService {
    @Inject
    ITagDAO tagDAO;

    @Override
    public List<TagResponse> findAll() {
        List<TagResponse> list = new ArrayList<TagResponse>();
        for (Tag tag : tagDAO.findAll()) {
            list.add(TagMapper.toTagResponse(tag));
        }
        return list;
    }

    @Override
    public TagResponse findById(Long id) {
        return TagMapper.toTagResponse(tagDAO.findById(id));
    }

    @Override
    public void addTag(TagRequest tagRequest) {
        tagDAO.addTag(TagMapper.toTagEntity(tagRequest));
    }

    @Override
    public void updateTag(TagRequest tagRequest) {
        tagDAO.updateTag(TagMapper.toTagEntity(tagRequest));
    }

    @Override
    public void deleteTag(Long id) {
        tagDAO.deleteTag(id);
    }
}
