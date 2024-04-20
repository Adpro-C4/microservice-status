package id.ac.ui.cs.advprog.statustrackingorder.model;

import org.junit.jupiter.api.Assertions;
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

    private Order order;


    @BeforeEach
    void setUp(){
        this.order = new Order();
        this.order.setOrderId(1L);
        this.order.setOrderName("Order Minuman");
    }



    @Test
    void testCreateStatusWithValidOrderAndStatus() {
        Status orderStatus = new Status(this.order,"Unverified");
        Assertions.assertEquals("Unverified", orderStatus.getStatus());
    }


    @Test
    public void testUpdateStatusWithValidNewStatusVerified() {
        Status orderStatus = Status.createStatus(this.order, "Unverified");
        String newStatus = "Verified";

        orderStatus.updateStatus(newStatus);

        Assertions.assertEquals(newStatus, orderStatus.getStatus());
    }

    @Test
    public void testUpdateStatusWithValidNewStatusCanceled() {
        Status orderStatus = Status.createStatus(this.order, "Unverified");
        String newStatus = "Canceled";

        orderStatus.updateStatus(newStatus);

        Assertions.assertEquals(newStatus, orderStatus.getStatus());

    }

    @Test
    public void testUpdateStatusWithEmptyNewStatus() {
        Status orderStatus = Status.createStatus(this.order, "Unverified");

        assertThrows(IllegalArgumentException.class, () -> {
            orderStatus.updateStatus("");
        });
    }

    @Test
    public  void testUpdateStatusWithInvalidStatus(){
        Status orderStatus = Status.createStatus(this.order, "Unverified");
        String newStatus = "Hacked";


        orderStatus.updateStatus(newStatus);
        Assertions.assertFalse(orderStatus.getStatus().equals("Verified") || orderStatus.getStatus().equals("Canceled"));

    }
    @Test
    void  testGetStatusByOrderWithExistingOrder() {
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


        Assertions.assertNotNull(foundStatus);
        Assertions.assertEquals(orderStatus1, foundStatus);
    }



    @Test
    void testGetStatusByOrderWithNonexistentOrder() {
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
        Assertions.assertNull(foundStatus);
    }

    @Test
    public void testGetStatusByOrderWithEmptyList() {
        // Arrange
        Order order1 = new Order();
        order1.setOrderId(1L);
        order1.setOrderName("Order BMW");

        List<Status> allStatus = new ArrayList<>();

        Status foundStatus = Status.getStatusByOrder(allStatus, order1);
        Assertions.assertNull(foundStatus);
    }

}

