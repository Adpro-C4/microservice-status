package id.ac.ui.cs.advprog.statustrackingorder.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import id.ac.ui.cs.advprog.statustrackingorder.model.Status;


public interface StatusService {

    public CompletableFuture<Status> createStatusAsync(Status status);

    public Status getStatusById(Long id);

    public Status getStatusByOrderId(String orderId);

    public void  updateStatus(Status status);
    public List<Status> getAllStatus();



}
