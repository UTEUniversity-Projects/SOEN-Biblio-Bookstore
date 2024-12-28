package com.biblio.mapper;

import com.biblio.dto.request.StaffRequest;
import com.biblio.dto.response.AddressResponse;
import com.biblio.dto.response.StaffResponse;
import com.biblio.entity.Account;
import com.biblio.entity.Address;
import com.biblio.entity.Staff;
import com.biblio.enumeration.EAccountStatus;
import com.biblio.enumeration.EGender;
import com.biblio.enumeration.EUserRole;
import com.biblio.utils.EnumUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class StaffMapper {
    public static Staff toStaffEntity(StaffRequest staffRequest) {
        Account account = new Account();
        account.setUsername(staffRequest.getUsername());
        account.setPassword(staffRequest.getPassword());
        account.setUserRole(EUserRole.STAFF);
        account.setStatus(EAccountStatus.ACTIVE);

        Set<Address> addresses = getAddresses(staffRequest);

        return Staff.builder()
                .id(Long.parseLong(staffRequest.getId()))
                .fullName(staffRequest.getFullName())
                .emailAddress(staffRequest.getEmailAddress())
                .phoneNumber(staffRequest.getPhoneNumber())
                .dateOfBirth(staffRequest.getDateOfBirth())
                .avatar(staffRequest.getAvatar())
                .joinAt(LocalDateTime.now())
                .account(account)
                .addresses(addresses)
                .build();
    }

    private static Set<Address> getAddresses(StaffRequest staffRequest) {
        Set<Address> addresses = new HashSet<>();
        Address addressOne = new Address();
        addressOne.setNation(staffRequest.getNationOne());
        addressOne.setProvince(staffRequest.getProvinceOne());
        addressOne.setDistrict(staffRequest.getDistrictOne());
        addressOne.setVillage(staffRequest.getVillageOne());
        addressOne.setDetail(staffRequest.getDetailOne());
        addresses.add(addressOne);
        Address addressTwo = new Address();
        addressTwo.setNation(staffRequest.getNationTwo());
        addressTwo.setProvince(staffRequest.getProvinceTwo());
        addressTwo.setDistrict(staffRequest.getDistrictTwo());
        addressTwo.setVillage(staffRequest.getVillageTwo());
        addressTwo.setDetail(staffRequest.getDetailTwo());
        addresses.add(addressTwo);
        return addresses;
    }

    public static StaffResponse toStaffResponse(Staff staff) {        
        Set<AddressResponse> addresses = new HashSet<>();
        for (Address address : staff.getAddresses()) {
            addresses.add(AddressMapper.toAddressResponse(address));
        }

        return StaffResponse.builder()
                .id(staff.getId().toString())
                .avatar(staff.getAvatar())
                .dateOfBirth(staff.getDateOfBirth())
                .emailAddress(staff.getEmailAddress())
                .fullName(staff.getFullName())
                .joinAt(staff.getJoinAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .phoneNumber(staff.getPhoneNumber())
                .status(staff.getAccount().getStatus().toString())
                .username(staff.getAccount().getUsername())
                .password(staff.getAccount().getPassword())
                .addresses(addresses)
                .build();
    }
}
