package id.ac.ui.cs.advprog.statustrackingorder.repository;

import id.ac.ui.cs.advprog.statustrackingorder.model.StatusApi;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatusApiRepositoryTest {
    StatusApiRepository statusApiRepository;
    List<StatusApi> allStatus;
    Map<Long, StatusApi> mapStatus;

    @BeforeEach
    void setUp() {
        statusApiRepository = new StatusApiRepository();

        // Setup data
        StatusApi status1 = new StatusApi(1L, "Unverified");
        StatusApi status2 = new StatusApi(2L, "Unverified");
        StatusApi status3 = new StatusApi(3L, "Unverified");

        allStatus = new ArrayList<>();
        allStatus.add(status1);
        allStatus.add(status2);
        allStatus.add(status3);

        mapStatus = new HashMap<>();
        mapStatus.put(status1.getId(), status1);
        mapStatus.put(status2.getId(), status2);
        mapStatus.put(status3.getId(), status3);
    }

    @Test
    void testSaveCreate() {
        StatusApi status = allStatus.get(1);

        StatusApi result = statusApiRepository.save(status);

        StatusApi findResult = statusApiRepository.findById(allStatus.get(1).getId());

        Assertions.assertEquals(status.getId(), result.getId());
        Assertions.assertEquals(status.getId(), findResult.getId());
        Assertions.assertEquals(status.getOrderStatus(), findResult.getOrderStatus());
    }

    @Test
    void testSaveUpdate() {
        StatusApi status = allStatus.get(1);
        statusApiRepository.save(status);

        // Using the same ID as an existing status
        StatusApi newStatus = new StatusApi(status.getId(), "Cancelled");

        StatusApi result = statusApiRepository.save(newStatus);

        StatusApi findResult = statusApiRepository.findById(allStatus.get(1).getId());
        Assertions.assertEquals(newStatus.getId(), result.getId());
        Assertions.assertEquals(newStatus.getId(), findResult.getId());
        Assertions.assertEquals(newStatus.getOrderStatus(), findResult.getOrderStatus());
    }

    @Test
    void testFindByIdIfIdFound() {
        for (StatusApi status : allStatus) {
            statusApiRepository.save(status);
        }

        StatusApi findResult = statusApiRepository.findById(allStatus.get(1).getId());
        Assertions.assertEquals(allStatus.get(1).getId(), findResult.getId());
        Assertions.assertEquals(allStatus.get(1).getOrderStatus(), findResult.getOrderStatus());
    }

    @Test
    void testFindByIdIfIdNotFound() {
        for (StatusApi status : allStatus) {
            statusApiRepository.save(status);
        }

        StatusApi findResult = statusApiRepository.findById(909090L);
        Assertions.assertNull(findResult);
    }

    @Test
    void testFindOrderStatusByIdWithNonexistentOrder() {
        // Create a status with a non-existent order ID
        StatusApi orderStatus1 = new StatusApi(1L, "Verified");

        statusApiRepository.save(orderStatus1);
        StatusApi foundStatus = statusApiRepository.findByOrderId(2L);

        Assertions.assertNull(foundStatus);
    }

    @Test
    void testDeleteById() {
        StatusApi status = allStatus.get(1);
        statusApiRepository.save(status);

        statusApiRepository.deleteById(status.getId());

        StatusApi findResult = statusApiRepository.findById(status.getId());
        Assertions.assertNull(findResult);
    }


}
