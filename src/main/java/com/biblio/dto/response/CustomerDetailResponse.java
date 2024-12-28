package com.biblio.dto.response;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CustomerDetailResponse {
    private Long id;
    private String avatar;
    private String dateOfBirth;
    private String email;
    private String fullName;
    private String gender;
    private String joinAt;
    private String phoneNumber;
    private String memberShip;
    private String status;
    private String username;
    private String password;
    @Builder.Default
    private Set<AddressResponse> addresses = new HashSet<>();

}
