package com.amk.lms.models.res.orders;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderRes {
    private Integer OrderId ;
    private Integer customerId;
    private Date orderDate;
    private Date shippedDate;
    private String shipName;
    private String shipAddress;
    private List<OrderDetailRes> orderDetailResList;

}
