package com.biblio.mapper;

import com.biblio.dto.response.AccountGetResponse;
import com.biblio.entity.Account;

public class AccountMapper {

    public static AccountGetResponse toAccountGetResponse(Account account) {
       return AccountGetResponse.builder()
               .id(account.getId())
               .username(account.getUsername())
               .password(account.getPassword())
               .role(account.getUserRole().toString())
               .status(account.getStatus().getValue())
               .build();
    }
  
}
