package id.ac.ui.cs.advprog.statustrackingorder.service;

import id.ac.ui.cs.advprog.statustrackingorder.model.TrackOrder;


public interface TrackOrderService {
    public  TrackOrder createTracking( TrackOrder trackOrder);

    public TrackOrder getTrackingById(String id);

    public TrackOrder getTrackingByOrderId(String orderId);

}