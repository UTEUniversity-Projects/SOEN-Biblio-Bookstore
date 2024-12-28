package com.biblio.dao;

import com.biblio.entity.ResponseSupport;

public interface IResponseSupportDAO {
    ResponseSupport findBySupportId(Long supportId);
    void save(ResponseSupport responseSupport);
}