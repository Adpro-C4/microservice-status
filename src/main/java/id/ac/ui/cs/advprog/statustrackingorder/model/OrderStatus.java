package id.ac.ui.cs.advprog.statustrackingorder.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class OrderStatus {
    private Long id;
    private Long order; // private Order order => sementara sebelum mikroservice Order selesai
    private String status;

    public OrderStatus() {
        // Constructor kosong
    }

    public OrderStatus(Long order, String status) {
        this.order = order;
        this.status = status;
    }

    public OrderStatus getOrderStatus() {
        return this;
    }

    public Long getStatusById() {
        return this.id;
    }

}