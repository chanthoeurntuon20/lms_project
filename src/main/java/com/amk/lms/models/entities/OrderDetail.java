package com.amk.lms.models.entities;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "tbl_order_detail",schema = "amkdb")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer orderDeId;
    @Column(name = "order_id")
    private Integer orderId;
    @Column(name = "unit_price")
    private Double unitPrice;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "discount")
    private Double discount;
//    @ManyToOne
//    @JoinColumn(name = "order_id")
//    private Order order;
}
