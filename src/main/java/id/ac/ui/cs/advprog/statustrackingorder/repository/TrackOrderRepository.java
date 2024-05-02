package id.ac.ui.cs.advprog.statustrackingorder.repository;

import id.ac.ui.cs.advprog.statustrackingorder.model.Status;
import id.ac.ui.cs.advprog.statustrackingorder.model.TrackOrder;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface TrackOrderRepository extends JpaRepository<TrackOrder, Long> {
    Optional<TrackOrder> findByOrderId(Long orderId);
}