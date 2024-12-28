package com.biblio.dao.impl;

import com.biblio.dao.IAddressDAO;
import com.biblio.dto.response.AddressResponse;
import com.biblio.entity.Address;

public class AddressDAOImpl extends GenericDAOImpl<Address> implements IAddressDAO {

    public AddressDAOImpl() {
        super(Address.class);
    }

    @Override
    public Address findById(Long id) {
        return super.findById(id);
    }
}
