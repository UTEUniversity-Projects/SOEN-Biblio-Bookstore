package com.biblio.dao.impl;

import com.biblio.dao.IReturnBookItemDAO;
import com.biblio.entity.ReturnBookItem;

public class ReturnBookItemDAOImpl extends GenericDAOImpl<ReturnBookItem> implements IReturnBookItemDAO {

    public ReturnBookItemDAOImpl() {
        super(ReturnBookItem.class);
    }


    @Override
    public ReturnBookItem save(ReturnBookItem returnBookItem) {
        return super.save(returnBookItem);
    }

}
