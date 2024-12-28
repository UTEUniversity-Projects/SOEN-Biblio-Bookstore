package com.biblio.dao.impl;

import com.biblio.dao.IEWalletDAO;
import com.biblio.entity.EWallet;

import java.util.HashMap;
import java.util.Map;

public class EWalletDAOImpl extends GenericDAOImpl<EWallet> implements IEWalletDAO {
    public EWalletDAOImpl() {
        super(EWallet.class);
    }
    @Override
    public EWallet findByOrderId(Long id) {
        String jpql = "SELECT e FROM EWallet e WHERE e.order.id = :id";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        return super.findSingleByJPQL(jpql, params);
    }

    public static void main(String[] args) {
        EWalletDAOImpl dao = new EWalletDAOImpl();
        EWallet ew = dao.findByOrderId(58L);
        System.out.println(ew);
    }
}
