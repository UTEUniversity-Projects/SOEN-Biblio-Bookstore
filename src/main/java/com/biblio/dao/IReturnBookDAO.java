package com.biblio.dao;

import com.biblio.entity.Book;
import com.biblio.entity.ReturnBook;
import com.biblio.entity.ReturnBookItem;

import java.util.List;

public interface IReturnBookDAO {

    ReturnBook findById(Long id);

    ReturnBook findByOrderId(Long orderId);

    ReturnBook save(ReturnBook returnBook);

    ReturnBook update(ReturnBook returnBook);
}
