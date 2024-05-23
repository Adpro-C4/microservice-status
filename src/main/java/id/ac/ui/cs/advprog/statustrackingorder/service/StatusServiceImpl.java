package id.ac.ui.cs.advprog.statustrackingorder.service;

import id.ac.ui.cs.advprog.statustrackingorder.enums.OrderStatus;
import id.ac.ui.cs.advprog.statustrackingorder.eventdriven.Producer;
import id.ac.ui.cs.advprog.statustrackingorder.model.Status;
import id.ac.ui.cs.advprog.statustrackingorder.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class StatusServiceImpl implements  StatusService {

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private Producer messageQueue;

    @Override
    @Async
    public CompletableFuture<Status> createStatusAsync(Status status) {
        // Simpan status secara asynchronous dan kembalikan hasilnya dalam CompletableFuture
        return CompletableFuture.supplyAsync(() -> statusRepository.save(status));
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
    public Status getStatusByOrderId(String orderId) {
        Optional<Status> statusOptional = statusRepository.findByOrderId(orderId);
        if (statusOptional.isPresent()) {
            Status status = statusOptional.get();
            return status;
        } else {
            throw new NoSuchElementException("No such product with id: " + orderId);
        }
    }
    @Override
    @Async
    public void updateStatus(Status newStatus) throws NoSuchElementException {
        Long id = newStatus.getId();
        Status fetchedStatus = getStatusById(id);
        if (fetchedStatus != null) {
            if(fetchedStatus.getOrderStatus().equals(OrderStatus.SELESAI.getDisplayName())){
                // sudah disetujui (selesai) tidak bisa ditolak
                // empty block
            }
            else{
                if(newStatus.getOrderStatus().equals(OrderStatus.SELESAI.getDisplayName())){ // order baru saja disetujui
                    // kirim notifikasi ke product microservice untuk update stok.
                    System.out.println("INIIIIIIIIIIII");
                    messageQueue.sendMessage("update-stok-produk-routing-key", fetchedStatus.getOrderId());
                }
                fetchedStatus.setOrderStatus(newStatus.getOrderStatus());
            }
            statusRepository.save(fetchedStatus);
        } else {
            throw new NoSuchElementException("No such status with id: " + id);
        }
    }

    @Override
    public List<Status> getAllStatus() {
        return statusRepository.findAll();
    }


}
