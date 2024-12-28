package com.biblio.service;

import com.biblio.entity.ResponseSupport;
import com.biblio.entity.Support;

import java.util.List;

public interface IResponseSupportService {
    public List<Support> getAllSupports();
    public Support getSupportById(Long id);
    public ResponseSupport getResponseSupportBySupportId(Long supportId);
    public void saveResponseSupport(String title, String content, Long staffId, Long supportId);
}
