package com.amk.lms.models.req.customers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
@Data
public class AccountReq {
    @JsonIgnore
    private Integer id;
    private Integer cusId;
    private String acctNo;
    @NonNull
    @NotEmpty(message = "The account name is required")
    private String acctName;
    @NotEmpty(message = "The account currency is required")
    private String acctCcy;
    @NotEmpty(message = "The amount is required")
    private double amount;
    private String status;
    private Date createdAt;
    private String createdBy;
}
