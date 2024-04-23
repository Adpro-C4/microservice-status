package id.ac.ui.cs.advprog.statustrackingorder.controller;

import id.ac.ui.cs.advprog.statustrackingorder.model.StatusApi;
import id.ac.ui.cs.advprog.statustrackingorder.service.StatusApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/status")
public class StatusApiController {

    private final StatusApiService statusApiService;

    @Autowired
    public StatusApiController(StatusApiService statusApiService) {
        this.statusApiService = statusApiService;
    }

    @PostMapping("/create")
    public ResponseEntity<StatusApi> createStatus(@RequestBody StatusApi statusApi) {
        StatusApi createdStatus = statusApiService.createStatus(statusApi.getOrderId(), statusApi.getOrderStatus());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStatus);
    }

    @GetMapping("find/{id}")
    public ResponseEntity<StatusApi> findStatusById(@PathVariable("id") Long id) {
        StatusApi statusApi = statusApiService.findStatusById(id);
        if (statusApi != null) {
            return ResponseEntity.ok(statusApi);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<StatusApi> updateStatus(@RequestBody StatusApi statusApi) {
        StatusApi updatedStatus = statusApiService.updateStatus(statusApi);
        return ResponseEntity.ok(updatedStatus);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteStatusById(@PathVariable("id") Long id) {
        statusApiService.deleteStatusById(id);
        return ResponseEntity.noContent().build();
    }
}
