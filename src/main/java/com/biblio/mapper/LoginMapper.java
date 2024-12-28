package com.biblio.mapper;

import com.biblio.dto.request.LoginRequest;
import com.biblio.dto.response.LoginResponse;
import com.biblio.entity.Account;

public class LoginMapper {

    public LoginRequest toLoginRequest(Account account) {
        return LoginRequest.builder()
                .username(account.getUsername())
                .password(account.getPassword())
                .build();
    }

    public LoginResponse toAccount(Account account) {
        return LoginResponse.builder()
                .username(account.getUsername())
                .password(account.getPassword())
                .role(account.getUserRole().toString())
                .status(account.getStatus().getValue())
                .build();
    }

}
