package id.ac.ui.cs.advprog.statustrackingorder.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class Status {
    private Long id;
    private Order order;
    private String orderStatus;

    public Status() {
        // Constructor kosong
    }

    public Status(Long id, Order order, String orderStatus){
        this.id = id;
        this.order =order;
        this.orderStatus = orderStatus;
    }

    public Status(Order order, String status) {
        this.order = order;
        this.orderStatus = status;
    }

    public static Status createStatus(Order order, String status) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        if(!status.equals("Cancelled") && !status.equals("Verified") && !status.equals("Unverified")){
            throw  new IllegalArgumentException("Invalid Status!");
        }
        return new Status(order, status);
    }

    public void updateStatus(String newStatus) {
        if (newStatus == null || newStatus.isEmpty()) {
            throw new IllegalArgumentException("New status cannot be empty");
        }
        if(!newStatus.equals("Cancelled") && !newStatus.equals("Verified") && !newStatus.equals("Unverified")){
            throw  new IllegalArgumentException("Invalid Status!");
        }

        this.orderStatus = newStatus;
    }

    public static Status getStatusByOrder(List<Status> allStatus, Order order) {
        for (Status status : allStatus) {
            if (status.getOrder().equals(order)) {
                return status;
            }
        }
        return null; // Return null jika order tidak ditemukan
    }


}
