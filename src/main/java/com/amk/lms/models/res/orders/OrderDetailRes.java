package com.amk.lms.models.res.orders;

import lombok.Data;

@Data
public class OrderDetailRes {
    private Integer Id;
    private Double unitPrice;
    private Integer quantity;
    private Double discount;
}
