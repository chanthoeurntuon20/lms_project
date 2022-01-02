package com.amk.lms.models.entities;

import lombok.Data;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name="et_customer",schema = "amkdb")
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "gender")
    private String gender;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "mobile_number")
    private String mobileNumber;
    @Column(name = "address")
    private String address;
    @Column(name = "status")
    private String status;

    @OneToMany()
    @JoinColumn(name = "cusId", referencedColumnName = "Id")
    private List<Account> accountList;

}
