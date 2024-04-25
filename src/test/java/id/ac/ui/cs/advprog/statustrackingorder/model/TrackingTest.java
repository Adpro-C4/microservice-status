package id.ac.ui.cs.advprog.statustrackingorder.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TrackingTest {

    @Test
    public void testGetterSetter_OrderId() {
        Tracking tracking = new Tracking();
        Long expectedOrderId = 12345L;

        tracking.setOrderId(expectedOrderId);

        assertEquals(expectedOrderId, tracking.getOrderId());
    }

    @Test
    public void testGetterSetter_Methode() {
        Tracking tracking = new Tracking();
        String expectedMethode = "JNE";

        tracking.setMethode(expectedMethode);

        assertEquals(expectedMethode, tracking.getMethode());
    }

    @Test
    public void testGetterSetter_ResiCode() {
        Tracking tracking = new Tracking();
        String expectedResiCode = "RESICODE123";

        tracking.setResiCode(expectedResiCode);

        assertEquals(expectedResiCode, tracking.getResiCode());
    }

    @Test
    public void testGetterSetter_TrackingId() {
        Tracking tracking = new Tracking();
        Long expectedTrackingId = 98765L;

        tracking.setTrackingId(expectedTrackingId);

        assertEquals(expectedTrackingId, tracking.getTrackingId());
    }
}
