package com.biblio.dto.request;

import com.biblio.enumeration.ENotificationType;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class NotificationInsertRequest {
    private String title;
    private String content;
    private String hyperLink;
    private ENotificationType type;
}
