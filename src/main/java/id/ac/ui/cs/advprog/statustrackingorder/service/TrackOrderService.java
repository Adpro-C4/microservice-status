package id.ac.ui.cs.advprog.statustrackingorder.service;

import java.util.concurrent.CompletableFuture;

import id.ac.ui.cs.advprog.statustrackingorder.model.TrackOrder;


public interface TrackOrderService {
    CompletableFuture<TrackOrder> createTrackingAsync( TrackOrder trackOrder);

    TrackOrder getTrackingById(String id);

    TrackOrder getTrackingByOrderId(String orderId);

}