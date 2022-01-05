package com.amk.lms.models.entities;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="tbl_order",schema= "amkdb")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer OrderId ;
    @Column(name = "cusId")
    private Integer customerId;
    @Column(name = "order_date")
    private Date orderDate;
    @Column(name = "shipped_date")
    private Date shippedDate;
    @Column(name = "ship_name")
    private String shipName;
    @Column(name = "ship_address")
    private String shipAddress;
    @OneToMany()
    @JoinColumn(name = "order_id")
    private List<OrderDetail> orderDetailList;
}
