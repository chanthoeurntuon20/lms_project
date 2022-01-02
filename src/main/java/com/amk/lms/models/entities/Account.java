package com.amk.lms.models.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "et_account", schema = "amkdb")
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "cusId")
    private Integer cusId;
    @Column(name = "acct_no")
    private String acctNo;
    @Column(name = "acct_name")
    private String acctName;
    @Column(name = "acct_ccy")
    private String acctCcy;
    @Column(name = "amount")
    private double amount;
    @Column(name = "status")
    private String status;
    @Column(name = "created_At")
    private Date createdAt;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "updated_At")
    private Date updatedAt;
    @Column(name = "updated_by")
    private String updatedBy;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="cusId", referencedColumnName = "Id")
//    private Customer customer;
}
