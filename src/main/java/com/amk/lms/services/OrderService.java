package com.amk.lms.services;

import com.amk.lms.exceptions.EtInternalServerErrorException;
import com.amk.lms.models.req.orders.OrderReq;
import com.amk.lms.models.res.orders.OrderRes;
import java.util.List;
public interface OrderService {
    List<OrderRes> findAll() throws EtInternalServerErrorException;
    OrderRes addOrder(OrderReq req) throws EtInternalServerErrorException;
    OrderRes findOrderById(Integer id) throws EtInternalServerErrorException;

    void addOrderDetail(OrderReq req) throws EtInternalServerErrorException;
//    void addOrderDetail(OrderReq req)throws EtInternalServerErrorException;
//    void updateOrder(Long id, Order order)throws EtInternalServerErrorException;
//    void updateOrderDetail(Long id, Order order)throws EtInternalServerErrorException;
//    void deleteOrder(Long orderId)throws EtInternalServerErrorException;
}
