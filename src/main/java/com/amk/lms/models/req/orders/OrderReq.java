package com.amk.lms.models.req.orders;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class OrderReq {
    @JsonIgnore
    private Integer OrderId ;
    private Integer customerId;
    private String shipName;
    private Date orderDate;
    private Date shippedDate;
    private String shipAddress;
    private List<OrderDetailReq> orderDetailReqList;
}
