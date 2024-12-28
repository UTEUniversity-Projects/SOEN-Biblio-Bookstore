package com.biblio.service;

import com.biblio.dto.request.SupportRequest;
import com.biblio.entity.Support;

import java.sql.SQLException;
import java.util.List;

public interface ISupportService {
    void createSupport(SupportRequest request) throws Exception;
}
