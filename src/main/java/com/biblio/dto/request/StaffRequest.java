package com.biblio.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StaffRequest {
    String id;
    String fullName;
    String emailAddress;
    String phoneNumber;
    String dateOfBirth;
    String avatar;
    String username;
    String password;
    String nationOne;
    String provinceOne;
    String districtOne;
    String villageOne;
    String detailOne;
    String nationTwo;
    String provinceTwo;
    String districtTwo;
    String villageTwo;
    String detailTwo;
}
