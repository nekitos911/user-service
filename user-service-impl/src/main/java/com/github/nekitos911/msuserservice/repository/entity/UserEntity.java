package com.github.nekitos911.msuserservice.repository.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "usr")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bankId;
    private String firstName;
    private String lastName;
    private String middleName;
    private LocalDate birthDate;
    private String passportNumber;
    private String birthPlace;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phone;
    private String registrationAddress;
    private String residentialAddress;
}
