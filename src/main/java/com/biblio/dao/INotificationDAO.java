package com.biblio.dao;

import com.biblio.entity.Notification;

public interface INotificationDAO {
    void saveSupport_Notification(Notification notification);

    Notification update(Notification notification);

    Notification findNotificationById(Long id);

    Notification insert(Notification notification);
}
