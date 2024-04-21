package id.ac.ui.cs.advprog.statustrackingorder.repository;

import id.ac.ui.cs.advprog.statustrackingorder.model.Status;

import java.util.HashMap;
import java.util.Map;

public class StatusRepository {
    private Map<Long, Status> statusMap;

    public StatusRepository() {
        this.statusMap = new HashMap<>();
    }

    public Status save(Status status) {
        statusMap.put(status.getId(), status);
        return status;
    }

    public Status findById(Long id) {
        return statusMap.get(id);
    }

    public Status update(Status status) {
        Long id = status.getId();
        if (id == null) {
            throw new IllegalArgumentException("Status ID cannot be null");
        }

        if (!statusMap.containsKey(id)) {
            throw new IllegalArgumentException("Status not found for update");
        }

        statusMap.put(id, status);
        return status;
    }

    public void deleteById(Long id) {
        statusMap.remove(id);
    }
}
