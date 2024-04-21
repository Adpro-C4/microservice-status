package id.ac.ui.cs.advprog.statustrackingorder.service;

import id.ac.ui.cs.advprog.statustrackingorder.model.Order;
import id.ac.ui.cs.advprog.statustrackingorder.model.Status;
import id.ac.ui.cs.advprog.statustrackingorder.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusService {

    @Autowired
    private StatusRepository statusRepository;

    public Status createStatus(Order order, String orderStatus) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        if (Status.isValidStatus(orderStatus)) {
            throw new IllegalArgumentException("Invalid Status!");
        }

        Status newStatus = Status.createStatus(order, orderStatus);
        return statusRepository.save(newStatus);
    }

    public Status findStatusById(Long id) {
        if (id != null) {
            return statusRepository.findById(id);
        } else {
            throw new IllegalArgumentException("Status ID cannot be null");
        }
    }

    public Status updateStatus(Status status) {
        if (status.getId() != null) {
            Status existingStatus = statusRepository.findById(status.getId());
            if (existingStatus != null) {
                if (!Status.isValidStatus(status.getOrderStatus())) {
                    return statusRepository.update(status);
                } else {
                    throw new IllegalArgumentException("Invalid Status!");
                }
            } else {
                throw new IllegalArgumentException("Status not found for update");
            }
        } else {
            throw new IllegalArgumentException("Status ID cannot be null");
        }
    }

    public void deleteStatusById(Long id) {
        if (id != null) {
            Status existingStatus = statusRepository.findById(id);
            if (existingStatus != null) {
                statusRepository.deleteById(id);
            } else {
                throw new IllegalArgumentException("Status not found for deletion");
            }
        } else {
            throw new IllegalArgumentException("Status ID cannot be null");
        }
    }
}
