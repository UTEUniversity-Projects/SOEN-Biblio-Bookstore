package com.biblio.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CustomerRegisterResponse {

    private String fullName;
    private String email;
    private String phoneNumber;
    private String dateOfBirth;
    private String gender;
    private String username;
    private String password;
    private String province;
    private String district;
    private String village;
    private String detail;
    private String avatar;
    private LocalDateTime joinAt;

}
