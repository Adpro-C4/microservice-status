package id.ac.ui.cs.advprog.statustrackingorder.controller;

import id.ac.ui.cs.advprog.statustrackingorder.model.TrackOrder;
import id.ac.ui.cs.advprog.statustrackingorder.service.TrackOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class TrackOrderControllerTest {

    @InjectMocks
    private TrackOrderController trackOrderController;

    @Mock
    private TrackOrderService trackOrderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTrackOrderById_Success() {
        TrackOrder trackOrder = new TrackOrder();
        when(trackOrderService.getTrackingById(anyString())).thenReturn(trackOrder);

        ResponseEntity<Object> response = trackOrderController.getTrackOrderById("123");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(trackOrder, response.getBody());
    }

    @Test
    public void testGetTrackOrderById_NotFound() {
        when(trackOrderService.getTrackingById(anyString())).thenThrow(new NoSuchElementException());

        ResponseEntity<Object> response = trackOrderController.getTrackOrderById("123");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No such status with id: 123", response.getBody());
    }

    @Test
    public void testGetStatusByOrderId_Success() {
        TrackOrder trackOrder = new TrackOrder();
        when(trackOrderService.getTrackingByOrderId(anyString())).thenReturn(trackOrder);

        ResponseEntity<Object> response = trackOrderController.getStatusByOderId("456");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(trackOrder, response.getBody());
    }

    @Test
    public void testGetStatusByOrderId_NotFound() {
        when(trackOrderService.getTrackingByOrderId(anyString())).thenThrow(new NoSuchElementException());

        ResponseEntity<Object> response = trackOrderController.getStatusByOderId("456");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No such status with id: 456", response.getBody());
    }

    @Test
    public void testCreateTrackingOrder_Success() {
        TrackOrder trackOrder = new TrackOrder();
        when(trackOrderService.createTrackingAsync(any(TrackOrder.class))).thenReturn(new CompletableFuture<>());

        ResponseEntity<Object> response = trackOrderController.createTrackingOrder(trackOrder);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Status created successfully", response.getBody());
    }
}
