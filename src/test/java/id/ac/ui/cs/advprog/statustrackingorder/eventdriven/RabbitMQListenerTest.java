package id.ac.ui.cs.advprog.statustrackingorder.eventdriven;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.statustrackingorder.enums.OrderStatus;
import id.ac.ui.cs.advprog.statustrackingorder.eventdriven.RabbitMQListener;
import id.ac.ui.cs.advprog.statustrackingorder.model.Status;
import id.ac.ui.cs.advprog.statustrackingorder.model.TrackOrder;
import id.ac.ui.cs.advprog.statustrackingorder.service.StatusService;
import id.ac.ui.cs.advprog.statustrackingorder.service.TrackOrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RabbitMQListenerTest {

    @Mock
    private TrackOrderService trackOrderService;

    @Mock
    private StatusService statusService;

    @InjectMocks
    private RabbitMQListener rabbitMQListener;

    @Test
    public void testReceiveOrderTrackingMessage() throws IOException {

        String message = "{\"orderId\": \"123456\", \"orderStatus\": \"PROCESSING\"}";
        TrackOrder order = new TrackOrder();
        order.setOrderId("123456");


        when(trackOrderService.createTrackingAsync(any(TrackOrder.class))).thenReturn(CompletableFuture.completedFuture(null));

        rabbitMQListener.receiveOrderTrackingMessage(message);

        verify(trackOrderService, times(1)).createTrackingAsync(any(TrackOrder.class));
        verify(statusService, times(1)).createStatusAsync(any(Status.class));
    }

    @Test
    public void testReceiveOrderTrackingMessage_ExceptionHandling() throws IOException {
        String message = "{\"orderId\": \"123456\", \"orderStatus\": \"PROCESSING\"}";

        when(trackOrderService.createTrackingAsync(any(TrackOrder.class))).thenThrow(new RuntimeException("Failed to create tracking"));

        rabbitMQListener.receiveOrderTrackingMessage(message);

        verify(trackOrderService, times(1)).createTrackingAsync(any(TrackOrder.class));
        verify(statusService, never()).createStatusAsync(any(Status.class)); // Ensure that statusService.createStatusAsync is not called if there's an exception
    }
}
