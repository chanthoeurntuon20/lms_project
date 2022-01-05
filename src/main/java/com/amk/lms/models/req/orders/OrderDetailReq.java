package com.amk.lms.models.req.orders;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class OrderDetailReq {
    @JsonIgnore
    private Integer Id;
    private Integer orderId;
    private Double unitPrice;
    private Integer quantity;
    private Double discount;
}
