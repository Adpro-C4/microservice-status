package id.ac.ui.cs.advprog.statustrackingorder.model;

import id.ac.ui.cs.advprog.statustrackingorder.model.StatusApi;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class StatusApiTest {
    private Long orderId;

    @BeforeEach
    void setUp() {
        this.orderId = 1L;
    }

    @Test
    void testCreateStatusWithValidOrderAndStatus() {
        StatusApi orderStatus = StatusApi.createStatus(orderId, "Unverified");
        Assertions.assertEquals("Unverified", orderStatus.getOrderStatus());
    }

    @Test
    void testUpdateStatusWithValidNewStatusVerified() {
        StatusApi orderStatus = StatusApi.createStatus(orderId, "Unverified");
        String newStatus = "Verified";

        orderStatus.updateStatus(newStatus);

        Assertions.assertEquals(newStatus, orderStatus.getOrderStatus());
    }

    @Test
    void testUpdateStatusWithValidNewStatusCanceled() {
        StatusApi orderStatus = StatusApi.createStatus(orderId, "Unverified");
        String newStatus = "Cancelled";

        orderStatus.updateStatus(newStatus);

        Assertions.assertEquals(newStatus, orderStatus.getOrderStatus());
    }

    @Test
    void testUpdateStatusWithEmptyNewStatus() {
        StatusApi orderStatus = StatusApi.createStatus(orderId, "Unverified");

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            orderStatus.updateStatus("");
        });
    }

    @Test
    void testUpdateStatusWithInvalidStatus() {
        StatusApi orderStatus = StatusApi.createStatus(orderId, "Unverified");
        String newStatus = "Hacked";

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            orderStatus.updateStatus(newStatus);
        });
    }

    @Test
    void testGetStatusByOrderWithExistingOrder() {
        List<StatusApi> allStatus = new ArrayList<>();
        StatusApi orderStatus = StatusApi.createStatus(orderId, "Verified");
        allStatus.add(orderStatus);

        StatusApi foundStatus = StatusApi.getStatusByOrder(allStatus, orderId);

        Assertions.assertNotNull(foundStatus);
        Assertions.assertEquals(orderStatus, foundStatus);
    }

    @Test
    void testGetStatusByOrderWithNonexistentOrder() {
        List<StatusApi> allStatus = new ArrayList<>();
        StatusApi orderStatus = StatusApi.createStatus(orderId, "Verified");
        allStatus.add(orderStatus);

        Long nonexistentOrderId = 2L;
        StatusApi foundStatus = StatusApi.getStatusByOrder(allStatus, nonexistentOrderId);

        Assertions.assertNull(foundStatus);
    }

    @Test
    void testGetStatusByOrderWithEmptyList() {
        List<StatusApi> allStatus = new ArrayList<>();

        StatusApi foundStatus = StatusApi.getStatusByOrder(allStatus, orderId);

        Assertions.assertNull(foundStatus);
    }
}
