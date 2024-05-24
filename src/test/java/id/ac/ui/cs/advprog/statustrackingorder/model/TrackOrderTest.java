package id.ac.ui.cs.advprog.statustrackingorder.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TrackOrderTest {

    @Test
    public void testTrackOrderGettersAndSetters() {
        TrackOrder trackOrder = new TrackOrder();
        trackOrder.setTrackingId("trk123");
        trackOrder.setOrderId("order123");
        trackOrder.setMethode("Express");
        trackOrder.setResiCode("resi123");

        assertEquals("trk123", trackOrder.getTrackingId());
        assertEquals("order123", trackOrder.getOrderId());
        assertEquals("Express", trackOrder.getMethode());
        assertEquals("resi123", trackOrder.getResiCode());
    }

    @Test
    public void testTrackOrderAllArgsConstructor() {
        TrackOrder trackOrder = new TrackOrder("trk456", "order456", "Standard", "resi456");

        assertEquals("trk456", trackOrder.getTrackingId());
        assertEquals("order456", trackOrder.getOrderId());
        assertEquals("Standard", trackOrder.getMethode());
        assertEquals("resi456", trackOrder.getResiCode());
    }

    @Test
    public void testTrackOrderNoArgsConstructor() {
        TrackOrder trackOrder = new TrackOrder();

        assertNull(trackOrder.getTrackingId());
        assertNull(trackOrder.getOrderId());
        assertNull(trackOrder.getMethode());
        assertNull(trackOrder.getResiCode());
    }
}