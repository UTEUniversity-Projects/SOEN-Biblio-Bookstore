package com.biblio.dao.impl;

import com.biblio.dao.IBankTransferDAO;
import com.biblio.dao.IEWalletDAO;
import com.biblio.entity.BankTransfer;
import com.biblio.entity.EWallet;

import java.util.HashMap;
import java.util.Map;

public class BankTransferDAOImpl extends GenericDAOImpl<BankTransfer> implements IBankTransferDAO {
    public BankTransferDAOImpl() {
        super(BankTransfer.class);
    }

    @Override
    public BankTransfer findByOrderId(Long id) {
        String jpql = "SELECT bt FROM BankTransfer bt WHERE bt.order.id = :id";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        return super.findSingleByJPQL(jpql, params);
    }

    public static void main(String[] args) {
        BankTransferDAOImpl dao = new BankTransferDAOImpl();
        BankTransfer ew = dao.findByOrderId(58L);
        System.out.println(ew);
    }
}
