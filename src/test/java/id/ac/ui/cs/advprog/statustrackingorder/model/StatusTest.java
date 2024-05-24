package id.ac.ui.cs.advprog.statustrackingorder.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StatusTest {

    @Test
    public void testStatusGettersAndSetters() {
        Status status = new Status();
        status.setId(1L);
        status.setOrderId("12345");
        status.setOrderStatus("Shipped");

        assertEquals(1L, status.getId());
        assertEquals("12345", status.getOrderId());
        assertEquals("Shipped", status.getOrderStatus());
    }

    @Test
    public void testStatusAllArgsConstructor() {
        Status status = new Status(2L, "54321", "Delivered");

        assertEquals(2L, status.getId());
        assertEquals("54321", status.getOrderId());
        assertEquals("Delivered", status.getOrderStatus());
    }

    @Test
    public void testStatusNoArgsConstructor() {
        Status status = new Status();

        assertNull(status.getId());
        assertNull(status.getOrderId());
        assertNull(status.getOrderStatus());
    }
}