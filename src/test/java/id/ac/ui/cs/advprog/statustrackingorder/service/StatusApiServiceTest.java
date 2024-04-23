package id.ac.ui.cs.advprog.statustrackingorder.service;

import id.ac.ui.cs.advprog.statustrackingorder.model.StatusApi;
import id.ac.ui.cs.advprog.statustrackingorder.repository.StatusApiRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

public class StatusApiServiceTest {

    @Mock
    private StatusApiRepository statusApiRepository;

    @InjectMocks
    private StatusApiService statusApiService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateStatusWithNullOrderId() {
        String orderStatus = "Verified";
        Assertions.assertThrows(IllegalArgumentException.class, () -> statusApiService.createStatus(null, orderStatus));
    }

    @Test
    void testCreateStatusWithInvalidStatus() {
        Long orderId = 1L;
        String orderStatus = "InvalidStatus";
        Assertions.assertThrows(IllegalArgumentException.class, () -> statusApiService.createStatus(orderId, orderStatus));
    }

    @Test
    void testUpdateStatusWithNonExistingStatus() {
        StatusApi status = new StatusApi(1L, "Unverified");
        when(statusApiRepository.findById(1L)).thenReturn(null);

        Assertions.assertThrows(IllegalArgumentException.class, () -> statusApiService.updateStatus(status));
    }

    @Test
    void testFindStatusByIdWithExistingId() {
        Long id = 1L;
        StatusApi expectedStatus = new StatusApi(id, "Verified");
        when(statusApiRepository.findById(id)).thenReturn(expectedStatus);

        StatusApi actualStatus = statusApiService.findStatusById(id);

        Assertions.assertEquals(expectedStatus, actualStatus);
    }

    @Test
    void testFindStatusByIdWithNullId() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> statusApiService.findStatusById(null));
    }

    @Test
    void testDeleteStatusByIdWithNonExistingId() {
        Long id = 100L;
        when(statusApiRepository.findById(id)).thenReturn(null);

        Assertions.assertThrows(IllegalArgumentException.class, () -> statusApiService.deleteStatusById(id));
    }

    // Anda bisa menambahkan lebih banyak pengujian sesuai kebutuhan
}
