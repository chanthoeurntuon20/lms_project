package com.amk.lms.models.req.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class UserReq {
    @JsonIgnore
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String roles;
}
