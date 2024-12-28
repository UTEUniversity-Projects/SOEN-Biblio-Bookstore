package com.biblio.service.impl;

import com.biblio.dao.ICustomerDAO;
import com.biblio.dao.INotificationDAO;
import com.biblio.dao.ISupportDAO;
import com.biblio.dao.impl.CustomerDAOImpl;
import com.biblio.dao.impl.NotificationDAOImpl;
import com.biblio.dao.impl.SupportDAOImpl;
import com.biblio.dto.request.SupportRequest;
import com.biblio.entity.Customer;
import com.biblio.entity.Notification;
import com.biblio.entity.Support;
import com.biblio.enumeration.ENotificationStatus;
import com.biblio.enumeration.ENotificationType;
import com.biblio.enumeration.ESupportStatus;
import com.biblio.mapper.SupportMapper;
import com.biblio.service.ISupportService;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

public class SupportServiceImpl implements ISupportService {

    private ISupportDAO supportDAO;
    private INotificationDAO notificationDAO;
    private ICustomerDAO customerDAO;
    public SupportServiceImpl(ISupportDAO supportDAO, INotificationDAO notificationDAO) {
        this.supportDAO = supportDAO;
        this.notificationDAO = notificationDAO;
    }
    public SupportServiceImpl() {
        this.supportDAO = new SupportDAOImpl();
        this.notificationDAO = new NotificationDAOImpl();
        this.customerDAO = new CustomerDAOImpl();

    }
    @Transactional
    @Override
    public void createSupport(SupportRequest request) {
        if (request == null || request.getTitle() == null || request.getFeedbackContent() == null) {
            throw new IllegalArgumentException("Request không hợp lệ: Tiêu đề hoặc nội dung không được để trống.");
        }

        Customer customer = customerDAO.findById(request.getCustomerId());  // Cần thay 'findById' bằng phương thức thích hợp của bạn

        if (customer == null) {
            throw new IllegalArgumentException("Không tìm thấy khách hàng với ID: " + request.getCustomerId());
        }
        // Map DTO sang Entity
        Support support = SupportMapper.toEntity(request, customer);
        System.out.println(support);
        // Lưu yêu cầu hỗ trợ vào cơ sở dữ liệu
        supportDAO.save(support);
    }






}

