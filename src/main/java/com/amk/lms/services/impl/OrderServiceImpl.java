package com.amk.lms.services.impl;

import com.amk.lms.Repositories.OrderDetailRepository;
import com.amk.lms.Repositories.OrderRepository;
import com.amk.lms.exceptions.EtAuthException;
import com.amk.lms.exceptions.EtInternalServerErrorException;
import com.amk.lms.models.entities.Order;
import com.amk.lms.models.entities.OrderDetail;
import com.amk.lms.models.req.orders.OrderReq;
import com.amk.lms.models.res.orders.OrderDetailRes;
import com.amk.lms.models.res.orders.OrderRes;
import com.amk.lms.services.OrderService;
import java.util.ArrayList;
import java.util.List;
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    public List<OrderRes> findAll() throws EtInternalServerErrorException {
        return null;
    }

    @Override
    public OrderRes addOrder(OrderReq req) throws EtInternalServerErrorException {
        try {
            Order order = new Order();
            order.setCustomerId(req.getCustomerId());
            order.setOrderDate(req.getOrderDate());
            order.setShipName(req.getShipName());
            order.setShippedDate(req.getShippedDate());
            order.setShipAddress(req.getShipAddress());
            this.orderRepository.saveAndFlush(order);
            var orderDeList = this.orderDetailRepository.findByOrderId(req.getOrderId());
            List<OrderDetail> orderResDeList = new ArrayList<>();
            for (int j = 0; j <= orderDeList.size();j++){
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderId(orderDeList.get(j).getOrderId());
                orderDetail.setQuantity(orderDeList.get(j).getQuantity());
                orderDetail.setUnitPrice(orderDeList.get(j).getUnitPrice());
                orderDetail.setDiscount(orderDeList.get(j).getDiscount());
                orderResDeList.add(orderDetail);
            }
            return  GetOrder(order);
        }catch (Exception ex){
            throw new EtAuthException(ex.getMessage());
        }
    }

    @Override
    public OrderRes findOrderById(Integer id) throws EtInternalServerErrorException {
        return null;
    }

    @Override
    public void addOrderDetail(OrderReq req) throws EtInternalServerErrorException {
        try{

            for (int i=0;i<=req.getOrderDetailReqList().size()-1;i++) {
                OrderDetail orderDe =new OrderDetail();
                orderDe.setOrderId(req.getOrderDetailReqList().get(i).getOrderId());
                orderDe.setQuantity(req.getOrderDetailReqList().get(i).getQuantity());
                orderDe.setUnitPrice(req.getOrderDetailReqList().get(i).getUnitPrice());
                orderDe.setDiscount(req.getOrderDetailReqList().get(i).getDiscount());
                orderDetailRepository.saveAndFlush(orderDe);
            }
        }catch (Exception ex){
            throw new EtAuthException(ex.getMessage());
        }

    }
    private OrderRes GetOrder(Order order){
        List<OrderDetailRes> orderResList=new ArrayList<>();
        OrderRes orderRes =new OrderRes();

        orderRes.setOrderId(order.getOrderId());
        orderRes.setOrderDate(order.getOrderDate());
        orderRes.setShipName(order.getShipName());
        orderRes.setShippedDate(order.getShippedDate());
        orderRes.setShipAddress(order.getShipAddress());
        for (int i=0;i<=order.getOrderDetailList().size()-1;i++) {
            OrderDetailRes orderDeRes =new OrderDetailRes();
            orderDeRes.setId(order.getOrderDetailList().get(i).getOrderDeId());
            orderDeRes.setQuantity(order.getOrderDetailList().get(i).getQuantity());
            orderDeRes.setUnitPrice(order.getOrderDetailList().get(i).getUnitPrice());
            orderDeRes.setDiscount(order.getOrderDetailList().get(i).getDiscount());
            orderResList.add(orderDeRes);
        }
        orderRes.setOrderDetailResList(orderResList);
        return  orderRes;
    }
}
