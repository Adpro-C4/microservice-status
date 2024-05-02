package id.ac.ui.cs.advprog.statustrackingorder.service;

import id.ac.ui.cs.advprog.statustrackingorder.model.Status;


public interface StatusService {
    public Status createStatus(Status status);

    public Status getStatusById(Long id);

    public Status getStatusByOrderId(Long orderId);

    public void  updateStatus(Status status);



}
