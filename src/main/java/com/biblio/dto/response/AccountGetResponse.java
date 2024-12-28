package com.biblio.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AccountGetResponse {
    private Long id;
    private String username;
    private String password;
    private String status;
    private String role;
}
