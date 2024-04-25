package id.ac.ui.cs.advprog.statustrackingorder.service;

import id.ac.ui.cs.advprog.statustrackingorder.model.Tracking;
import id.ac.ui.cs.advprog.statustrackingorder.repository.TrackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class TrackingService {
    private final TrackingRepository trackingRepository;

    @Autowired
    public TrackingService(TrackingRepository trackingRepository) {
        this.trackingRepository = trackingRepository;
    }

    public void createTracking(Tracking tracking) throws SQLException {
        trackingRepository.createTracking(tracking);
    }

    public Optional<Tracking> findTrackingById(Long trackingId) throws SQLException {
        return trackingRepository.findTrackingById(trackingId);
    }

    public List<Tracking> findTrackingByOrderId(Long orderId) throws SQLException {
        return trackingRepository.findTrackingByOrderId(orderId);
    }

    public List<Tracking> findAllTracking() throws SQLException {
        return trackingRepository.findAllTracking();
    }
}
