package com.biblio.mapper;

import com.biblio.dto.request.CustomerInformationRequest;
import com.biblio.dto.request.CustomerRegisterRequest;
import com.biblio.dto.response.*;
import com.biblio.entity.Account;
import com.biblio.entity.Address;
import com.biblio.entity.Customer;
import com.biblio.entity.User;
import com.biblio.enumeration.EAccountStatus;
import com.biblio.enumeration.EGender;
import com.biblio.enumeration.EMembership;
import com.biblio.enumeration.EUserRole;
import com.biblio.utils.BCryptUtil;

import java.time.LocalDateTime;
import java.util.Optional;

public class CustomerMapper {
    public static Customer toCustomer(CustomerRegisterRequest request) {

        Customer customer = Customer.builder()
                .fullName(request.getFullName())
                .emailAddress(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .dateOfBirth(request.getDateOfBirth())
                .gender(EGender.valueOf(request.getGender()))
                .account(Account.builder()
                        .username(request.getUsername())
                        .password(BCryptUtil.HashPassword(request.getPassword()))
                        .userRole(EUserRole.CUSTOMER)
                        .status(EAccountStatus.ACTIVE)
                        .build())
                .avatar(request.getAvatar())
                .joinAt(LocalDateTime.now())
                .membership(EMembership.MEMBER)
                .address(Address.builder()
                        .nation("Việt Nam")
                        .province(request.getProvince())
                        .district(request.getDistrict())
                        .village(request.getVillage())
                        .detail(request.getDetail())
                        .customer(null)
                        .build())
                .build();

        customer.getAddresses().forEach(address -> address.setCustomer(customer));

        return customer;
    }

    public static CustomerRegisterResponse toCustomerRegisterResponse(Customer customer) {

        Optional<Address> optionalAddress = customer.getAddresses().stream().findFirst();

        return CustomerRegisterResponse.builder()
                .fullName(customer.getFullName())
                .email(customer.getEmailAddress())
                .dateOfBirth(customer.getDateOfBirth())
                .gender(customer.getGender() != null ? customer.getGender().toString() : null)
                .username(customer.getAccount() != null ? customer.getAccount().getUsername() : null)
                .password(customer.getAccount() != null ? customer.getAccount().getPassword() : null)
                .province(optionalAddress.map(Address::getProvince).orElse(null))
                .district(optionalAddress.map(Address::getDistrict).orElse(null))
                .village(optionalAddress.map(Address::getVillage).orElse(null))
                .detail(optionalAddress.map(Address::getDetail).orElse(null))
                .avatar(customer.getAvatar())
                .phoneNumber(customer.getPhoneNumber() != null ? String.valueOf(customer.getPhoneNumber()) : null)
                .joinAt(customer.getJoinAt())
                .build();
    }

    public static CustomerGetListResponse toCustomerGetListResponse(Customer customer) {
        return CustomerGetListResponse.builder()
                .id(customer.getId())
                .fullName(customer.getFullName())
                .email(customer.getEmailAddress())
                .status(customer.getAccount().getStatus().toString())
                .orderCount((long) customer.getOrders().size())
                .build();

    }

    public static CustomerDetailResponse toCustomerDetailResponse(Customer customer) {

        CustomerDetailResponse customerDetailResponse = CustomerDetailResponse.builder()
                .id(customer.getId())
                .avatar(customer.getAvatar())
                .dateOfBirth(customer.getDateOfBirth())
                .email(customer.getEmailAddress())
                .fullName(customer.getFullName())
                .gender(customer.getGender().getDisplayName())
                .joinAt(customer.getJoinAt().toString())
                .phoneNumber(customer.getPhoneNumber())
                .memberShip(customer.getMembership().toString())
                .status(customer.getAccount().getStatus().toString())
                .username(customer.getAccount().getUsername())
                .password(customer.getAccount().getPassword())
                .build();

        for (Address address : customer.getAddresses()) {
            customerDetailResponse.getAddresses().add(AddressMapper.toAddressResponse(address));
        }

        return customerDetailResponse;
    }

    public static CustomerResponse toCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .avatar(customer.getAvatar())
                .fullName(customer.getFullName())
                .email(customer.getEmailAddress())
                .phoneNumber(customer.getPhoneNumber())
                .build();
    }
    public static CustomerReportResponse toCustomerReportResponse(Customer customer) {
        CustomerReportResponse customerReportResponse = new CustomerReportResponse();
        customerReportResponse.setId(customer.getId());
        customerReportResponse.setJoinAt(customer.getJoinAt());
        return customerReportResponse;
    }

    public static NewCustomerResponse toNewCustomerResponse(Customer customer) {
        return NewCustomerResponse.builder()
                .id(customer.getId())
                .jointAt(customer.getJoinAt())
                .build();
    }

    public static Customer toCustomerInformationRequest(CustomerInformationRequest request) {

        // Chuyển đổi từ chuỗi thành EAccountStatus
        EAccountStatus accountStatus = fromValue(request.getAccount().getStatus());

        Account account = Account.builder()
                .id(request.getAccount().getId())
                .username(request.getAccount().getUsername())
                .password(request.getAccount().getPassword())
                .userRole(EUserRole.valueOf(request.getAccount().getRole()))
                .status(accountStatus)  // Sử dụng giá trị đã chuyển đổi
                .build();

        Customer customer = Customer.builder()
                .id(request.getId())
                .avatar(request.getAvatar())
                .dateOfBirth(String.valueOf(request.getDateOfBirth()))
                .emailAddress(request.getEmail())
                .fullName(request.getFullName())
                .gender(request.getGender())
                .joinAt(LocalDateTime.parse(request.getJoinAt()))
                .phoneNumber(request.getPhoneNumber())
                .membership(EMembership.valueOf(request.getMemberShip()))
                .account(account)
                .build();

        return customer;
    }

    // Phương thức chuyển đổi chuỗi thành EAccountStatus
    public static EAccountStatus fromValue(String value) {
        for (EAccountStatus status : EAccountStatus.values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
