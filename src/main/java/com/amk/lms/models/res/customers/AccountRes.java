package com.amk.lms.models.res.customers;

import lombok.Data;

import java.util.Date;
@Data
public class AccountRes {
    private Integer id;
    private String acctNo;
    private String acctCcy;
    private double amount;
    private  CustomerRes customerRes;
}
