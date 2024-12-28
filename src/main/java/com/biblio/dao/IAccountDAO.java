package com.biblio.dao;

import com.biblio.entity.Account;

public interface IAccountDAO {

    boolean existsByUsername(String username);

    Account getAccountByUsername(String username);

    Account resetPassword(Account account);

}
