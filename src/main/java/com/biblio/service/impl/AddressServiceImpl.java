package com.biblio.service.impl;

import com.biblio.dao.IAddressDAO;
import com.biblio.dto.response.AddressResponse;
import com.biblio.mapper.AddressMapper;
import com.biblio.service.IAddressService;

import javax.inject.Inject;

public class AddressServiceImpl implements IAddressService {

    @Inject
    private IAddressDAO addressDAO;

    @Override
    public AddressResponse getAddressById(Long id) {
        return AddressMapper.toAddressResponse(addressDAO.findById(id));
    }
}
