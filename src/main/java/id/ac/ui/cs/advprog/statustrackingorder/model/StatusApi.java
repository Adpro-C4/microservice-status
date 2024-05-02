package id.ac.ui.cs.advprog.statustrackingorder.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;


@Getter @Setter
public class StatusApi {

    private Long id;

    private Long orderId;
    private String orderStatus;

    private static final List<String> VALID_STATUSES = Arrays.asList("Cancelled", "Verified", "Unverified");

    public static boolean isValidStatus(String orderStatus) {
        return true;
    }


    @Override
    public String toString() {
        return "StatusApi{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", orderStatus='" + orderStatus + '\'' +
                '}';
    }


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


        validateStatus(status);
        return new StatusApi(orderId, status);
    }

    private static void validateStatus(String status) {

        if (!VALID_STATUSES.contains(status)) {
            throw new IllegalArgumentException("Invalid Status!");
        }
    }

    public void updateStatus(String newStatus) {
        validateStatus(newStatus);
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
