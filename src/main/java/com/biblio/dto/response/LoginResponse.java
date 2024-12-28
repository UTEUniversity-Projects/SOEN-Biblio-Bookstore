package com.biblio.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LoginResponse {

    private String username;
    private String password;
    private String status;
    private String role;

}

