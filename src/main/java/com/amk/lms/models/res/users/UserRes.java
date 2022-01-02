package com.amk.lms.models.res.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class UserRes {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    @JsonIgnore
    private String password;
    private String roles;
    private String channelCode;
}
