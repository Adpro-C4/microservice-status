package id.ac.ui.cs.advprog.statustrackingorder.eventdriven;

import java.io.IOException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import id.ac.ui.cs.advprog.statustrackingorder.enums.OrderStatus;
import id.ac.ui.cs.advprog.statustrackingorder.model.Status;
import id.ac.ui.cs.advprog.statustrackingorder.model.TrackOrder;
import id.ac.ui.cs.advprog.statustrackingorder.service.StatusService;
import id.ac.ui.cs.advprog.statustrackingorder.service.TrackOrderService;

@Component
public class RabbitMQListener {

    @Autowired TrackOrderService trackOrderService;
    @Autowired StatusService statusService;

    @RabbitListener(queues = "purchase-queue")
    public void receivePurchaseMessage(String message) {
        System.out.println("Received message for purchase: " + message);
    }

    @RabbitListener(queues = "tracking-order-queue")
    public void receiveOrderTrackingMessage(String message) {
        System.out.println("Received message for tracking: " + message);
        try {
            System.out.println(message);
            ObjectMapper objectMapper = new ObjectMapper();
            TrackOrder order = objectMapper.readValue(message, TrackOrder.class);
            trackOrderService.createTracking(order);
            Status status = new Status();
            status.setOrderId(order.getOrderId());
            status.setOrderStatus(OrderStatus.MENUNGGU_PERSETUJUAN_ADMIN.getDisplayName());
            statusService.createStatus(status);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            System.out.println(message);
            e.printStackTrace();
        }
    }
}