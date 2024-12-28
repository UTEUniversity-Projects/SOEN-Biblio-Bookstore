package com.biblio.entity;

import com.biblio.enumeration.ENotificationStatus;
import com.biblio.enumeration.ENotificationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "notification")
@NoArgsConstructor
@Getter
@Setter
public class Notification implements Serializable {

    // region Attributes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "sent_time", nullable = false)
    private LocalDateTime sentTime;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "hyper_link", nullable = true)
    private String hyperlink;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ENotificationType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ENotificationStatus status;

    //endregion

    // region Relationships

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Staff staff;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Owner owner;

    // endregion
}
