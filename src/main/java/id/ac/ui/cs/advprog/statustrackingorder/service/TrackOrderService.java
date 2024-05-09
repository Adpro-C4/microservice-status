package id.ac.ui.cs.advprog.statustrackingorder.service;

import java.util.concurrent.CompletableFuture;

import id.ac.ui.cs.advprog.statustrackingorder.model.TrackOrder;


public interface TrackOrderService {
    public  CompletableFuture<TrackOrder> createTrackingAsync( TrackOrder trackOrder);

    public TrackOrder getTrackingById(String id);

    public TrackOrder getTrackingByOrderId(String orderId);

}