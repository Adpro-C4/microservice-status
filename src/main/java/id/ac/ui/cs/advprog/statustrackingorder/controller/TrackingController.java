package id.ac.ui.cs.advprog.statustrackingorder.controller;

import id.ac.ui.cs.advprog.statustrackingorder.model.Tracking;
import id.ac.ui.cs.advprog.statustrackingorder.service.TrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tracking")
public class TrackingController {
    private final TrackingService trackingService;

    @Autowired
    public TrackingController(TrackingService trackingService) {
        this.trackingService = trackingService;
    }

    @PostMapping("/create")
    public void createTracking(@RequestBody Tracking tracking) throws SQLException {
        trackingService.createTracking(tracking);
    }

    @GetMapping("/findById/{trackingId}")
    public Optional<Tracking> findTrackingById(@PathVariable Long trackingId) throws SQLException {
        return trackingService.findTrackingById(trackingId);
    }

    @GetMapping("/findByOrderId/{orderId}")
    public List<Tracking> findTrackingByOrderId(@PathVariable Long orderId) throws SQLException {
        return trackingService.findTrackingByOrderId(orderId);
    }

    @GetMapping("/findAll")
    public List<Tracking> findAllTracking() throws SQLException {
        return trackingService.findAllTracking();
    }
}
