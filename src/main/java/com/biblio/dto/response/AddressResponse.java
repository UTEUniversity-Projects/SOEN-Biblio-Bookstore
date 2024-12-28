package com.biblio.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class AddressResponse {
    private Long id;
    private String nation;
    private String province;
    private String district;
    private String village;
    private String detail;

    public String getFullAddress() {
        return String.format("%s, %s, %s, %s", detail, village, district, province);
    }
}

