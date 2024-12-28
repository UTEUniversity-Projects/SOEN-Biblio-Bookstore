package com.biblio.dao;

import com.biblio.entity.Tag;

import java.util.List;

public interface ITagDAO {

    List<Tag> findAll();

    Tag findById(Long id);

    void addTag(Tag tag);

    void updateTag(Tag tag);

    void deleteTag(Long id);

}
