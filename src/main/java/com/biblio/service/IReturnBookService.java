package com.biblio.service;

import com.biblio.dto.request.ReturnBookRequest;
import com.biblio.dto.request.ReturnOrderRequest;
import com.biblio.dto.response.ReturnBookManagementResponse;
import com.biblio.entity.ReturnBook;

import java.util.List;

public interface IReturnBookService {
  
    ReturnBookManagementResponse findReturnBookByOrderId(Long orderId);

    void save(ReturnOrderRequest request);

    boolean update(Long returnBookId);

    boolean saveReturnOrder(ReturnOrderRequest returnOrderRequest);
  
}
