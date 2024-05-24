package id.ac.ui.cs.advprog.statustrackingorder.eventdriven;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import id.ac.ui.cs.advprog.statustrackingorder.enums.OrderStatus;
import id.ac.ui.cs.advprog.statustrackingorder.model.Status;
import id.ac.ui.cs.advprog.statustrackingorder.model.TrackOrder;
import id.ac.ui.cs.advprog.statustrackingorder.service.StatusService;
import id.ac.ui.cs.advprog.statustrackingorder.service.TrackOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class RabbitMQListener {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQListener.class);

    private final TrackOrderService trackOrderService;
    private final StatusService statusService;

    @Autowired
    public RabbitMQListener(TrackOrderService trackOrderService, StatusService statusService) {
        this.trackOrderService = trackOrderService;
        this.statusService = statusService;
    }

    @RabbitListener(queues = "purchase-queue")
    public void receivePurchaseMessage(String message) {
        logger.info("Received message for purchase: {}", message);
    }

    @RabbitListener(queues = "tracking-order-queue")
    public void receiveOrderTrackingMessage(String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            TrackOrder order = objectMapper.readValue(message, TrackOrder.class);
            CompletableFuture.runAsync(() -> {
                trackOrderService.createTrackingAsync(order);
            });
            CompletableFuture.runAsync(() -> {
                Status status = new Status();
                status.setOrderId(order.getOrderId());
                status.setOrderStatus(OrderStatus.MENUNGGU_PERSETUJUAN_ADMIN.getDisplayName());
                statusService.createStatusAsync(status);
            });

        } catch (IOException e) {
            logger.error("Error processing message", e);
        } catch (Exception e) {
            logger.error("Unexpected error", e);
        }
    }
}
