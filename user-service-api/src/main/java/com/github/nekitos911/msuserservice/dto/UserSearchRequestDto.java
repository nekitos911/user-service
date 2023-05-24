package com.github.nekitos911.msuserservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class UserSearchRequestDto {
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String phone;

}
