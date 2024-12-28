package com.biblio.dao;

import com.biblio.entity.BankTransfer;
import com.biblio.entity.EWallet;

public interface IBankTransferDAO {
    BankTransfer findByOrderId(Long id);
}
