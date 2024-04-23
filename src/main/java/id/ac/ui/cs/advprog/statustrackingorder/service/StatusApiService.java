package id.ac.ui.cs.advprog.statustrackingorder.service;


import id.ac.ui.cs.advprog.statustrackingorder.model.StatusApi;
import id.ac.ui.cs.advprog.statustrackingorder.repository.StatusApiRepository;

import id.ac.ui.cs.advprog.statustrackingorder.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusApiService {

    @Autowired
    private StatusApiRepository statusApiRepository;

    public StatusApi createStatus(Long orderId, String orderStatus) {
        if (orderId == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        if (Status.isValidStatus(orderStatus)) {
            throw new IllegalArgumentException("Invalid Status!");
        }

        StatusApi newStatus = StatusApi.createStatus(orderId, orderStatus);
        return statusApiRepository.save(newStatus);
    }

    public StatusApi findStatusById(Long id) {
        if (id != null) {
            return statusApiRepository.findById(id);
        } else {
            throw new IllegalArgumentException("Status ID cannot be null");
        }
    }

    public StatusApi getStatusByOrderId(Long orderId) {
        List<StatusApi> allStatus = statusApiRepository.getAllStatus();
        return StatusApi.getStatusByOrder(allStatus, orderId);
    }

    public StatusApi updateStatus(StatusApi status) {
        if (status.getId() != null) {
            StatusApi existingStatus = statusApiRepository.findById(status.getId());
            if (existingStatus != null) {
                if (!Status.isValidStatus(status.getOrderStatus())) {
                    return statusApiRepository.update(status);
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
            StatusApi existingStatus = statusApiRepository.findById(id);
            if (existingStatus != null) {
                statusApiRepository.deleteById(id);
            } else {
                throw new IllegalArgumentException("Status not found for deletion");
            }
        } else {
            throw new IllegalArgumentException("Status ID cannot be null");
        }
    }



}
