package id.ac.ui.cs.advprog.statustrackingorder.repository;

import id.ac.ui.cs.advprog.statustrackingorder.model.OrderStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderStatusRepository {
    private final Map<Long, OrderStatus> orderStatusMap = new HashMap<>();
    private long nextId = 1;

    public List<OrderStatus> findAll() {
        return new ArrayList<>(orderStatusMap.values());
    }

    public OrderStatus add(OrderStatus orderStatus) {
        orderStatus.setId(nextId);
        orderStatusMap.put(nextId, orderStatus);
        nextId++;
        return orderStatus;
    }

    public OrderStatus update(OrderStatus orderStatus) {
        if (orderStatusMap.containsKey(orderStatus.getId())) {
            orderStatusMap.put(orderStatus.getId(), orderStatus);
            return orderStatus;
        }
        return null; // Or throw an exception if not found
    }

    public OrderStatus findById(Long id) {
        return orderStatusMap.get(id);
    }
}
