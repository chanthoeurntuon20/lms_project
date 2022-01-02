package com.amk.lms.models.req.customers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class CustomerReq {
    @JsonIgnore
    private Integer id;
    private String name;
    private String gender;
    private String email;
    private String phoneNumber;
    private String mobileNumber;
    private String address;
    private String status;

    private List<AccountReq> accountReqList;
}
