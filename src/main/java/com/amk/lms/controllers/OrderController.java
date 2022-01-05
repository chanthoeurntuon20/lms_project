package com.amk.lms.controllers;

import com.amk.lms.exceptions.EtAuthException;
import com.amk.lms.exceptions.EtInternalServerErrorException;
import com.amk.lms.models.req.orders.OrderReq;
import com.amk.lms.models.res.orders.OrderRes;
import com.amk.lms.services.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order/v1/")
public class OrderController {
//    @Autowired
    private OrderServiceImpl orderService;

    @PostMapping("create")
    public ResponseEntity<OrderRes> AddCustomer(@RequestBody OrderReq req) throws EtAuthException {
        try {
            OrderRes order = orderService.addOrder(req);

            req.setOrderId(order.getOrderId());
            orderService.addOrderDetail(req);

            order = orderService.findOrderById(order.getOrderId());
            return new ResponseEntity<OrderRes>(order, HttpStatus.OK);
        } catch (Exception ex) {
            throw new EtInternalServerErrorException(ex.getMessage().toString());
        }
    }
}
