package com.biblio.service.impl;

import com.biblio.dao.IAccountDAO;
import com.biblio.dto.request.ResetPasswordRequest;
import com.biblio.dto.response.AccountGetResponse;
import com.biblio.entity.Account;
import com.biblio.mapper.AccountMapper;
import com.biblio.service.IAccountService;

import javax.inject.Inject;

public class AccountServiceImpl implements IAccountService {

    @Inject
    private IAccountDAO accountDAO;

    @Override
    public boolean isUsernameExisted(String username) {
        return accountDAO.existsByUsername(username);
    }

    @Override
    public AccountGetResponse getAccountByUsername(String username) {
        Account account = accountDAO.getAccountByUsername(username);
        return AccountMapper.toAccountGetResponse(account);
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {
        Account account = accountDAO.getAccountByUsername(request.getUsername());
        account.setPassword(request.getNewPassword());
        accountDAO.resetPassword(account);
    }
}
