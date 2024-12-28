package com.biblio.mapper;

import com.biblio.dto.request.NotificationInsertRequest;
import com.biblio.dto.response.NotificationGetResponse;
import com.biblio.entity.Notification;
import com.biblio.enumeration.ENotificationStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NotificationMapper {

    public static NotificationGetResponse toNotificationGetResponse(Notification notification) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        String createdAtFormatted = notification.getCreatedAt().format(formatter);
        String sentTimeFormatted = notification.getSentTime().format(formatter);

        return NotificationGetResponse.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .content(notification.getContent())
                .type(notification.getType())
                .status(notification.getStatus())
                .hyperLink(notification.getHyperlink())
                .createdAt(createdAtFormatted)
                .sentTime(sentTimeFormatted)
                .build();
    }

    public static Notification toNotification(NotificationInsertRequest notificationInsertRequest) {
        Notification notification = new Notification();


        notification.setTitle(notificationInsertRequest.getTitle());
        notification.setContent(notificationInsertRequest.getContent());
        notification.setType(notificationInsertRequest.getType());
        notification.setHyperlink(notificationInsertRequest.getHyperLink());

        notification.setCreatedAt(LocalDateTime.now());
        notification.setSentTime(LocalDateTime.now());

        notification.setStatus(ENotificationStatus.NOT_SEEN);


        return notification;
    }
}
