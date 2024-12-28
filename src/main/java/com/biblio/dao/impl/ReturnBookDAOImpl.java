package com.biblio.dao.impl;

import com.biblio.dao.IReturnBookDAO;
import com.biblio.entity.Book;
import com.biblio.entity.ReturnBook;
import com.biblio.entity.ReturnBookItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReturnBookDAOImpl extends GenericDAOImpl<ReturnBook> implements IReturnBookDAO {

    public ReturnBookDAOImpl() {
        super(ReturnBook.class);
    }

    @Override
    public ReturnBook findById(Long id) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT r ")
                .append("FROM ReturnBook r ")
                .append("LEFT JOIN FETCH r.proof p ")
                .append("WHERE r.id = :id");

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        return super.findSingleByJPQL(jpql.toString(), params);
    }

    @Override
    public ReturnBook findByOrderId(Long orderId) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT r ")
                .append("FROM ReturnBook r ")
                .append("LEFT JOIN FETCH r.proof p ")
                .append("WHERE r.order.id = :orderId");

        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);

        return super.findSingleByJPQL(jpql.toString(), params);
    }
  
    @Override
    public ReturnBook save(ReturnBook returnBook) {
        return super.save(returnBook);
    }

    @Override
    public ReturnBook update(ReturnBook returnBook) {
        return super.update(returnBook);
    }

    public static void main(String[] args) {
        ReturnBookDAOImpl dao = new ReturnBookDAOImpl();
        ReturnBook returnBook = dao.findById(1L);
        System.out.println(returnBook);
    }
}
