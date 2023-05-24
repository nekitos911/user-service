package com.github.nekitos911.msuserservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.nekitos911.msuserservice.validator.constraints.UserEmailConstraint;
import com.github.nekitos911.msuserservice.validator.constraints.UserPassportConstraint;
import com.github.nekitos911.msuserservice.validator.constraints.UserPhoneConstraint;
import com.github.nekitos911.msuserservice.validator.markers.BankGroup;
import com.github.nekitos911.msuserservice.validator.markers.GosUslugiGroup;
import com.github.nekitos911.msuserservice.validator.markers.MailGroup;
import com.github.nekitos911.msuserservice.validator.markers.MobileGroup;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class UserDto {

    @NotNull(groups = {BankGroup.class, GosUslugiGroup.class})
    private Long bankId;

    @NotBlank(groups = {MailGroup.class, BankGroup.class, GosUslugiGroup.class})
    private String firstName;

    @NotBlank(groups = {BankGroup.class, GosUslugiGroup.class})
    private String lastName;

    @NotBlank(groups = {BankGroup.class, GosUslugiGroup.class})
    private String middleName;

    @NotNull(groups = {BankGroup.class, GosUslugiGroup.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate birthDate;

    @NotBlank(groups = {BankGroup.class, GosUslugiGroup.class})
    @UserPassportConstraint
    private String passportNumber;

    @NotBlank(groups = GosUslugiGroup.class)
    private String birthPlace;

    @NotBlank(groups = {MailGroup.class, GosUslugiGroup.class})
    @UserEmailConstraint
    private String email;

    @NotBlank(groups = {MobileGroup.class, GosUslugiGroup.class})
    @UserPhoneConstraint
    private String phone;

    @NotBlank(groups = GosUslugiGroup.class)
    private String registrationAddress;

    private String residentialAddress;
}
