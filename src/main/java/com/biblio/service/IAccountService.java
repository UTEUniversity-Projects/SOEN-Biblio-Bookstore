package com.biblio.service;

import com.biblio.dto.request.ResetPasswordRequest;
import com.biblio.dto.response.AccountGetResponse;

public interface IAccountService {

    boolean isUsernameExisted(String username);

    AccountGetResponse getAccountByUsername(String username);

    void resetPassword(ResetPasswordRequest request);
}
