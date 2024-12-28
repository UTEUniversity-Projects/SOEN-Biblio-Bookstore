package com.biblio.mapper;

import com.biblio.dto.response.AddressResponse;
import com.biblio.entity.Address;

public class AddressMapper {

    public static AddressResponse toAddressResponse(Address address) {
        AddressResponse addressResponse = new AddressResponse();
        addressResponse.setId(address.getId());
        addressResponse.setNation(address.getNation());
        addressResponse.setProvince(address.getProvince());
        addressResponse.setDistrict(address.getDistrict());
        addressResponse.setVillage(address.getVillage());
        addressResponse.setDetail(address.getDetail());
        return addressResponse;
    }

    public static Address toAddress(AddressResponse addressResponse) {
        return Address.builder()
                .id(addressResponse.getId())
                .nation(addressResponse.getNation())
                .province(addressResponse.getProvince())
                .district(addressResponse.getDistrict())
                .village(addressResponse.getVillage())
                .detail(addressResponse.getDetail())
                .build();
    }

}
