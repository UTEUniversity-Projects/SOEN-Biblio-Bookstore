package com.biblio.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class CustomerResponse {
    private long id;
    private String avatar;
    private String fullName;
    private String email;
    private String phoneNumber;
}