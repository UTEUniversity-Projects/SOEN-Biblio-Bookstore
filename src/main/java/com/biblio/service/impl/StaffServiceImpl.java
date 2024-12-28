package com.biblio.service.impl;

import com.biblio.dao.IStaffDAO;
import com.biblio.dto.request.NotificationInsertRequest;
import com.biblio.dto.request.StaffRequest;
import com.biblio.dto.response.NotificationGetResponse;
import com.biblio.dto.response.StaffResponse;
import com.biblio.entity.Notification;
import com.biblio.entity.Staff;
import com.biblio.enumeration.EAccountStatus;
import com.biblio.mapper.NotificationMapper;
import com.biblio.mapper.StaffMapper;
import com.biblio.service.IStaffService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StaffServiceImpl implements IStaffService {
    @Inject
    IStaffDAO staffDAO;

    @Override
    @Transactional
    public List<StaffResponse> findAll() {
        List<StaffResponse> list = new ArrayList<StaffResponse>();
        for (Staff staff : staffDAO.findAll()) {
            list.add(StaffMapper.toStaffResponse(staff));
        }
        return list;
    }

    @Override
    public StaffResponse findById(Long id) {
        return StaffMapper.toStaffResponse(staffDAO.findById(id));
    }

    @Override
    public void addStaff(StaffRequest staffRequest) {
        staffDAO.addStaff(StaffMapper.toStaffEntity(staffRequest));
    }

    @Override
    public void updateStaff(StaffRequest staffRequest) {

    }

    @Override
    public void deleteStaff(Long id) {

    }

    @Override
    public void activateStaff(Long id) {
        Staff staff = staffDAO.findById(id);
        staff.getAccount().setStatus(EAccountStatus.ACTIVE);
        staffDAO.activateStaff(staff);
    }

    @Override
    public void deactivateStaff(Long id) {
        Staff staff = staffDAO.findById(id);
        staff.getAccount().setStatus(EAccountStatus.INACTIVE);
        staffDAO.deactivateStaff(staff);
    }

    @Override
    public Long findIdStaffByAccountId(Long accountId) {
        return staffDAO.findByAccountId(accountId).getId();
    }

    @Override
    public List<NotificationGetResponse> getAllNotificationByStaffId(Long id) {
        Staff staff = staffDAO.findById(id);
        List<NotificationGetResponse> notifications = new ArrayList<>();

        for (Notification notification : staff.getNotifications()) {
            notifications.add(NotificationMapper.toNotificationGetResponse(notification));
        }
        Collections.reverse(notifications);
        return notifications;
    }

    @Override
    public void addNewNotification(NotificationInsertRequest notificationInsertRequest) {
        List<Staff> staffs = staffDAO.findAll();
        for (Staff staff : staffs) {
            Notification notification = NotificationMapper.toNotification(notificationInsertRequest);
            notification.setStaff(staff);
            staff.getNotifications().add(notification);
            staffDAO.updateStaff(staff);
        }
    }

    @Override
    public StaffResponse getStaffByUsername(String username) {
        return StaffMapper.toStaffResponse(staffDAO.findByUsername(username));
    }
}
