package id.ac.ui.cs.advprog.statustrackingorder.controller;

import id.ac.ui.cs.advprog.statustrackingorder.model.TrackOrder;
import id.ac.ui.cs.advprog.statustrackingorder.service.TrackOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/trackorder/")
public class TrackOrderController {

    @Autowired
    private TrackOrderService trackOrderService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTrackOrderById(@PathVariable("id") String id) {
        try {
            TrackOrder tracking = trackOrderService.getTrackingById(id);
            return ResponseEntity.ok(tracking);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such status with id: " + id);
        }
    }

    @GetMapping("/orderid/{id}")
    public ResponseEntity<Object> getStatusByOderId(@PathVariable("id") String orderId) {
        try {
            TrackOrder tracking = trackOrderService.getTrackingByOrderId(orderId);
            return ResponseEntity.ok(tracking);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such status with id: " + orderId);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createTrackingOrder(@RequestBody TrackOrder tracking) {
        trackOrderService.createTracking(tracking);
        return ResponseEntity.ok("Status created successfully");
    }

}
