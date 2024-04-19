package id.ac.ui.cs.advprog.statustrackingorder.service;

import id.ac.ui.cs.advprog.statustrackingorder.model.OrderStatus;

import java.util.List;

public interface OrderStatusService {
    List<OrderStatus> findAll();

    OrderStatus add(OrderStatus order);

    void remove(String orderId);

    OrderStatus update(OrderStatus order);

    OrderStatus findById(String orderId);
}
