package com.biblio.dto.response;

import com.biblio.enumeration.ENotificationStatus;
import com.biblio.enumeration.ENotificationType;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class NotificationGetResponse {
    private Long id;
    private String content;
    private String title;
    private ENotificationType type;
    private ENotificationStatus status;
    private String hyperLink;
    private String createdAt;
    private String sentTime;
}
