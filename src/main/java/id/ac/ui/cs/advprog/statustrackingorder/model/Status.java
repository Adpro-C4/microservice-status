package id.ac.ui.cs.advprog.statustrackingorder.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class Status {
    private Long id;
    private Order order;
    private String status;

    public Status() {
        // Constructor kosong
    }

    public Status(Order order, String status) {
        this.order = order;
        this.status = status;
    }

    public static Status createStatus(Order order, String status) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        return new Status(order, status);
    }

    public void updateStatus(String newStatus) {
        if (newStatus == null || newStatus.isEmpty()) {
            throw new IllegalArgumentException("New status cannot be empty");
        }
        // Tambahkan logika validasi status lainnya di sini sesuai kebutuhan

        this.status = newStatus;
    }

    public static Status getStatusByOrder(List<Status> allStatus, Order order) {
        for (Status status : allStatus) {
            if (status.getOrder().equals(order)) {
                return status;
            }
        }
        return null; // Return null jika order tidak ditemukan
    }

    // Getter dan setter diimplementasikan oleh Lombok
}
