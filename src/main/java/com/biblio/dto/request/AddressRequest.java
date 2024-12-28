package com.biblio.dto.request;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class AddressRequest {
    private Long id;
    private String nation;
    private String province;
    private String district;
    private String village;
    private String detail;
}
