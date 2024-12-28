package com.biblio.dao;

import com.biblio.entity.EWallet;

public interface IEWalletDAO {
    EWallet findByOrderId(Long id);
}
