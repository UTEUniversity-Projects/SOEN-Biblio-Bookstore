package com.biblio.dao;

import com.biblio.entity.Support;

import java.util.List;

public interface ISupportDAO {

    List<Support> findAll();

    Support findById(Long id);

    //void update(Support support);
    Support save(Support support) ;
}
