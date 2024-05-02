package id.ac.ui.cs.advprog.statustrackingorder.service;

import id.ac.ui.cs.advprog.statustrackingorder.model.Status;
import id.ac.ui.cs.advprog.statustrackingorder.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class StatusServiceImpl implements  StatusService {

    @Autowired
    private StatusRepository statusRepository;

    @Override
    public Status createStatus(Status status) {

        return statusRepository.save(status);
    }

    @Override
    public Status getStatusById(Long id) {
        Optional<Status> statusOptional = statusRepository.findById(id);
        if (statusOptional.isPresent()) {
            Status status = statusOptional.get();
            return status;
        } else {
            throw new NoSuchElementException("No such product with id: " + id);
        }
    }

    @Override
    public Status getStatusByOrderId(Long orderId) {
        Optional<Status> statusOptional = statusRepository.findByOrderId(orderId);
        if (statusOptional.isPresent()) {
            Status status = statusOptional.get();
            return status;
        } else {
            throw new NoSuchElementException("No such product with id: " + orderId);
        }
    }
    @Override
    public void updateStatus(Status existingStatus) throws NoSuchElementException {
        Long id = existingStatus.getId();
        Status fetchedStatus = getStatusById(id);
        if (fetchedStatus != null) {
            fetchedStatus.setOrderStatus(existingStatus.getOrderStatus());
            statusRepository.save(fetchedStatus);
        } else {
            throw new NoSuchElementException("No such status with id: " + id);
        }
    }



}
