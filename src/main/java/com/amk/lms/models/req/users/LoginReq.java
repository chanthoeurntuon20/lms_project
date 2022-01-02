package com.amk.lms.models.req.users;

import lombok.Data;

@Data
public class LoginReq {
    private String userName;
    private String password;
}
