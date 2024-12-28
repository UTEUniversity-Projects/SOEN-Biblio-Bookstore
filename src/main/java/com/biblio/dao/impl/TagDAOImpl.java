package com.biblio.dao.impl;

import com.biblio.dao.ITagDAO;
import com.biblio.entity.Tag;

import java.util.List;

public class TagDAOImpl extends GenericDAOImpl<Tag> implements ITagDAO {

    public TagDAOImpl() {
        super(Tag.class);
    }

    @Override
    public List<Tag> findAll() {
        return super.findAll();
    }

    @Override
    public Tag findById(Long id) {
        return super.findById(id);
    }

    @Override
    public void addTag(Tag tag) {
        super.save(tag);
    }

    @Override
    public void updateTag(Tag tag) {
        super.update(tag);
    }

    @Override
    public void deleteTag(Long id) {
        super.delete(id);
    }

}
