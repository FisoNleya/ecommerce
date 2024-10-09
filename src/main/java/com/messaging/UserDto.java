package com.messaging;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto implements Serializable {
    private Integer userId;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private String profileUrl;
    private Role role;
    private List<UserRightDto> userRights;
    private List<FeatureDto> features;
}