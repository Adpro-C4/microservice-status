package id.ac.ui.cs.advprog.statustrackingorder.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.*;

public class OrderTest{
    private Order order;
    private List<Order> orders;

    @BeforeEach
    void setUp() {
        this.order = new Order();
        this.order.setOrderId(1L);
        this.order.setOrderName("Order Minuman");

        orders = new ArrayList<>();
        Order order1 = new Order();
        order1.setOrderId(2L);
        order1.setOrderName("Order BMW");
        Order order2 = new Order();
        order2.setOrderId(3L);
        order2.setOrderName("Order Merci");

        orders.add(order1);
        orders.add(order2);
    }
}

class StatusTest {

    private Order orders;
    private List<Order> orders;

    @BeforeEach
    void setUp(){
        this.orders = new Order();
        this.orders.setOrderId(1L);
        this.orders.setOrderName("Order Minuman");
    }

    @Test
    void testCreateStatusWithEmptyOrder(){
        assertThrows(IllegalArgumentException.class, () -> {
            Status status = new Status(null , "Unverified");
        });
    }

    @Test
    public void testCreateStatusWithValidOrderAndStatus() {
        Status orderStatus = Status.createStatus(orders,"Unverified");

        assertNotNull(orderStatus);
        assertEquals(order, orderStatus.getOrder());
        assertEquals(status, orderStatus.getStatus());
    }

    @Test
    public void testUpdateStatusWithValidNewStatusVerified() {
        OrderStatus orderStatus = OrderStatus.createStatus(orders, "Unverified");
        String newStatus = "Verified";

        orderStatus.updateStatus(newStatus);

        assertEquals(newStatus, orderStatus.getStatus());
    }

    @Test
    public void testUpdateStatusWithValidNewStatusCanceled() {
        OrderStatus orderStatus = OrderStatus.createStatus(orders, "Unverified");
        String newStatus = "Canceled";

        orderStatus.updateStatus(newStatus);

        assertEquals(newStatus, orderStatus.getStatus());
    }

    @Test
    public void testUpdateStatusWithEmptyNewStatus() {
        OrderStatus orderStatus = OrderStatus.createStatus(orders, "Unverified");
        orderStatus.updateStatus("");

        assertThrows(IllegalArgumentException.class, () -> {
            orderStatus.updateStatus(newStatus);
        });
    }

    @Test
    public  void testUpdateStatusWithInvalidStatus(){
        OrderStatus orderStatus = OrderStatus.createStatus(orders, "Unverified");
        String newStatus = "Hacked";

        assertThrows(IllegalArgumentException.class, () -> {
            orderStatus.updateStatus(newStatus);
        });
    }
    @Test
    public void testGetStatusByOrderWithExistingOrder() {
        List<Status> allStatus = new ArrayList<>();

        Order order1 = new Order();
        order1.setOrderId(1L);
        order1.setOrderName("Order BMW");
        Status orderStatus1 = Status.createStatus(order1, "Verified");

        Order order2 = new Order();
        order2.setOrderId(2L);
        order2.setOrderName("Order Merci");
        Status orderStatus2 = Status.createStatus(order2, "Verified");

        allStatus.add(orderStatus1);
        allStatus.add(orderStatus2);


        Status foundStatus = Status.getStatusByOrder(allStatus, order1);


        assertNotNull(foundStatus);
        assertEquals(orderStatus1, foundStatus);
    }

    @Test
    public void testGetStatusByOrderWithNonexistentOrder() {
        List<Status> allStatus = new ArrayList<>();

        Order order1 = new Order();
        order1.setOrderId(1L);
        order1.setOrderName("Order BMW");
        Status orderStatus1 = Status.createStatus(order1, "Verified");
        allStatus.add(orderStatus1);

        Order order2 = new Order();
        order2.setOrderId(2L);
        order2.setOrderName("Order Merci");
        Status orderStatus2 = Status.createStatus(order2, "Verified");

        Status foundStatus = Status.getStatusByOrder(allStatus, order2);
        assertNull(foundStatus);
    }

    @Test
    public void testGetStatusByOrderWithEmptyList() {
        // Arrange
        Order order1 = new Order();
        order1.setOrderId(1L);
        order1.setOrderName("Order BMW");

        List<Status> allStatus = new ArrayList<>();

        Status foundStatus = Status.getStatusByOrder(allStatus, order);
        assertNull(foundStatus);
    }

}


