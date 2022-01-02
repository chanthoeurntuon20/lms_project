package com.amk.lms.models.res.customers;

import lombok.Data;

import java.util.List;

@Data
public class CustomerRes {
    private Integer id;
    private String name;
    private String gender;
    private String email;
    private String phoneNumber;
    private String mobileNumber;
    private String address;

    private List<AccountRes> accountResList;
}
