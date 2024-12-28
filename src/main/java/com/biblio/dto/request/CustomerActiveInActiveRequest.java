package com.biblio.dto.request;

import com.biblio.enumeration.EAccountStatus;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CustomerActiveInActiveRequest {
    private Long id;
    private EAccountStatus status;
//    private String avatar;
//    private String dateOfBirth;
//    private String email;
//    private String fullName;
//    private String gender;
//    private String joinAt;
//    private String phoneNumber;
//    private EMembership memberShip;
//
//    private String username;
//    private String password;
}
