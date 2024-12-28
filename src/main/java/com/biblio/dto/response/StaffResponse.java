package com.biblio.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StaffResponse {
    String id;
    String avatar;
    String dateOfBirth;
    String emailAddress;
    String fullName;
    String gender;
    String joinAt;
    String phoneNumber;
    String status;
    String username;
    String password;
    Set<AddressResponse> addresses = new HashSet<>();
}
