package id.ac.ui.cs.advprog.statustrackingorder.repository;

import id.ac.ui.cs.advprog.statustrackingorder.model.StatusApi;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StatusApiRepository {
    private Map<Long, StatusApi> statusMap;

    public StatusApiRepository() {
        this.statusMap = new HashMap<>();
    }

    public StatusApi save(StatusApi status) {
        statusMap.put(status.getId(), status);
        return status;
    }

    public StatusApi findById(Long id) {
        return statusMap.get(id);
    }

    public StatusApi findByOrderId(Long orderId){
        return statusMap.get(orderId);
    }

    public StatusApi update(StatusApi status) {
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

    public List<StatusApi> getAllStatus() {
        return new ArrayList<>(statusMap.values());
    }
}
