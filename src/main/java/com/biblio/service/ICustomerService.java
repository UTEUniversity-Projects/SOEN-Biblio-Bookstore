package com.biblio.service;

import com.biblio.dto.request.CustomerInformationRequest;
import com.biblio.dto.request.CustomerRegisterRequest;
import com.biblio.dto.request.NotificationInsertRequest;
import com.biblio.dto.response.*;
import com.biblio.entity.Customer;

import java.time.LocalDateTime;
import java.util.List;

public interface ICustomerService {

    List<CustomerGetListResponse> findAll();

    void deactivateCustomer(Long id);

    void activateCustomer(Long id);

    CustomerRegisterResponse addCustomer(CustomerRegisterRequest request);

    CustomerDetailResponse findById(Long id);

    boolean isEmailExisted(String email);

    boolean isPhoneNumberExisted(String phoneNumber);

    CustomerDetailResponse getCustomerDetailByUsername(String username);

    Long countCustomersJointAtTime(LocalDateTime start, LocalDateTime end);

    List<Customer> findAllCustomers();
    List<CountCustomerJoinResponse>countNewCustomersAtTime(LocalDateTime start, LocalDateTime end);

    Customer updateCustomer(CustomerInformationRequest customer);

    List<NotificationGetResponse> getAllNotificationByCustomerId(Long id);

    void addNewNotification(NotificationInsertRequest notificationInsertRequest, Long orderId);
}
