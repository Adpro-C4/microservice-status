package id.ac.ui.cs.advprog.statustrackingorder.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import id.ac.ui.cs.advprog.statustrackingorder.model.Status;


public interface StatusService {

   CompletableFuture<Status> createStatusAsync(Status status);

    Status getStatusById(Long id);

    Status getStatusByOrderId(String orderId);

    void  updateStatus(Status status);
    List<Status> getAllStatus();



}
