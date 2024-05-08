package id.ac.ui.cs.advprog.statustrackingorder.repository;

import id.ac.ui.cs.advprog.statustrackingorder.model.TrackOrder;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;


public interface TrackOrderRepository extends JpaRepository<TrackOrder, String> {
    Optional<TrackOrder> findByOrderId(String orderId);
}