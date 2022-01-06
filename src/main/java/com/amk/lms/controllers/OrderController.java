package com.amk.lms.controllers;

import com.amk.lms.exceptions.EtAuthException;
import com.amk.lms.exceptions.EtInternalServerErrorException;
import com.amk.lms.exceptions.EtResourceNotFoundException;
import com.amk.lms.models.req.orders.OrderReq;
import com.amk.lms.models.res.customers.CustomerRes;
import com.amk.lms.models.res.orders.OrderRes;
import com.amk.lms.services.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order/v1/")
public class OrderController {
    @Autowired
    private OrderServiceImpl orderService;

    @PostMapping("create")
    public ResponseEntity<OrderRes> AddOrder(@RequestBody OrderReq req) throws EtAuthException {
        try {
            OrderRes order = orderService.addOrder(req);
            req.setOrderId(order.getOrderId());
            orderService.addOrderDetail(req);
            order = orderService.findOrderById(order.getOrderId());
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (Exception ex) {
            throw new EtInternalServerErrorException(ex.getMessage().toString());
        }
    }
    @GetMapping("orders")
    public ResponseEntity<List<OrderRes>> GetOrderList() throws EtAuthException{
        try{
            List<OrderRes> resList = orderService.findAll();
            return new ResponseEntity<List<OrderRes>>(resList,HttpStatus.OK);

        }catch (Exception ex){
            throw new EtResourceNotFoundException(ex.getMessage().toString());
        }
    }
}
