package com.biblio.entity;

import com.biblio.enumeration.EGender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class User implements Serializable {

    // region Attributes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    protected String fullName;

    @Column(name = "email_address", nullable = false, unique = true)
    protected String emailAddress;

    @Column(name = "date_of_birth", nullable = false)
    protected String dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    protected EGender gender;

    @Column(name = "phone_number", nullable = false, unique = true)
    protected String phoneNumber;

    @Column(name = "avatar", nullable = false)
    protected String avatar;

    @Column(name = "join_at", nullable = false)
    protected LocalDateTime joinAt;

    // endregion

}
