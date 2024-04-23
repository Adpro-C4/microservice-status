package id.ac.ui.cs.advprog.statustrackingorder.controller;

import id.ac.ui.cs.advprog.statustrackingorder.model.Status;
import id.ac.ui.cs.advprog.statustrackingorder.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/status")
public class StatusController {

    private final StatusService statusService;

    @Autowired
    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @PostMapping("/create")
    public ResponseEntity<Status> createStatus(@RequestBody Status status) {
        Status createdStatus = statusService.createStatus(status.getOrder(), status.getOrderStatus());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStatus);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Status> findStatusById(@PathVariable("id") Long id) {
        Status status = statusService.findStatusById(id);
        if (status != null) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


//    @GetMapping("/statusbyorder/{orderId}")
//    public ResponseEntity<Status> getStatusByOrder(@PathVariable("orderId") Long orderId) {
//        Status status = statusService.getStatusByOrder(orderId);
//        if (status != null) {
//            return ResponseEntity.ok(status);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }


//    @GetMapping("/allstatus")
//    public ResponseEntity<List<Status>> getAllStatus() {
//        List<Status> allStatus = statusService.getAllStatus();
//        return ResponseEntity.ok(allStatus);
//    }

    @PutMapping("/update")
    public ResponseEntity<Status> updateStatus(@RequestBody Status status) {
        Status updatedStatus = statusService.updateStatus(status);
        return ResponseEntity.ok(updatedStatus);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteStatusById(@PathVariable("id") Long id) {
        statusService.deleteStatusById(id);
        return ResponseEntity.noContent().build();
    }
}
