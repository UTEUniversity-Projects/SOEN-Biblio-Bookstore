package com.biblio.service.impl;

import com.biblio.dao.INotificationDAO;
import com.biblio.dao.IStaffDAO;
import com.biblio.dto.request.NotificationInsertRequest;
import com.biblio.entity.Notification;
import com.biblio.entity.Staff;
import com.biblio.enumeration.ENotificationStatus;
import com.biblio.mapper.NotificationMapper;
import com.biblio.service.INotificationService;

import javax.inject.Inject;
import java.util.List;

public class NotificationServiceImpl implements INotificationService {

    @Inject
    INotificationDAO notificationDAO;

    @Inject
    IStaffDAO staffDAO;

    @Override
    public void updateStatusNotificationById(Long id) {
        Notification notification = notificationDAO.findNotificationById(id);
        notification.setStatus(ENotificationStatus.VIEWED);
        notificationDAO.update(notification);
    }
}
