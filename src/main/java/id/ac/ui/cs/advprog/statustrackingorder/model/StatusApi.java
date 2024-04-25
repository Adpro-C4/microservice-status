package id.ac.ui.cs.advprog.statustrackingorder.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class StatusApi {
    private Long id;
    private Long orderId;

    @Override
    public String toString() {
        return "StatusApi{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", orderStatus='" + orderStatus + '\'' +
                '}';
    }

    private String orderStatus;

    public StatusApi() {
        // Constructor kosong
    }

    public StatusApi(Long id, Long orderId, String orderStatus){
        this.id = id;
        this.orderId =orderId;
        this.orderStatus = orderStatus;
    }

    public StatusApi(Long orderId, String status) {
        this.orderId = orderId;
        this.orderStatus = status;
    }

    public static StatusApi createStatus(Long orderId, String status) {
        if (orderId == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        if(!status.equals("Cancelled") && !status.equals("Verified") && !status.equals("Unverified")){
            throw  new IllegalArgumentException("Invalid Status!");
        }
        return new StatusApi(orderId, status);
    }

    public static boolean isValidStatus(String orderStatus) {
        if(!orderStatus.equals("Cancelled") && !orderStatus.equals("Verified") && !orderStatus.equals("Unverified")){
            return true;
        }
        return false;
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

    public static StatusApi getStatusByOrder(List<StatusApi> allStatus, Long orderId) {
        for (StatusApi status : allStatus) {
            if (status.getOrderId().equals(orderId)) {
                return status;
            }
        }
        return null;
    }


}
