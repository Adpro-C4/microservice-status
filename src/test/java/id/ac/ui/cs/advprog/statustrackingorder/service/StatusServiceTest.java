package id.ac.ui.cs.advprog.statustrackingorder.service;

import id.ac.ui.cs.advprog.statustrackingorder.model.Order;
import id.ac.ui.cs.advprog.statustrackingorder.model.Status;
import id.ac.ui.cs.advprog.statustrackingorder.repository.StatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class StatusServiceTest {

    @Mock
    private StatusRepository statusRepository;

    @InjectMocks
    private StatusService statusService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }



    @Test
    void testCreateStatusWithNullOrder() {
        String orderStatus = "Verified";
        assertThrows(IllegalArgumentException.class, () -> statusService.createStatus(null, orderStatus));
    }

    @Test
    void testCreateStatusWithInvalidStatus() {
        Order order = new Order();
        order.setOrderId(1L);
        order.setOrderName("Order BMW");
        String orderStatus = "InvalidStatus";
        assertThrows(IllegalArgumentException.class, () -> statusService.createStatus(order, orderStatus));
    }

    @Test
    void testUpdateStatusWithNonExistingStatus() {
        Order order = new Order();
        order.setOrderId(1L);
        order.setOrderName("Order BMW");

        String orderStatus = "Cancelled";
        Status updatedStatus = new Status(1L, order, orderStatus);

        assertThrows(IllegalArgumentException.class, () -> statusService.updateStatus(updatedStatus));
    }

    @Test
    void testFindStatusByIdWithExistingId() {
        Order order = new Order();
        order.setOrderId(1L);
        order.setOrderName("Order BMW");
        String orderStatus = "Verified";
        Status expectedStatus = new Status(1L, order, orderStatus);

        when(statusRepository.findById(1L)).thenReturn(expectedStatus);

        Status actualStatus = statusService.findStatusById(1L);

        assertEquals(expectedStatus, actualStatus);
        verify(statusRepository).findById(1L);
    }

    @Test
    void testDeleteStatusByIdWithNonExistingId() {
        assertThrows(IllegalArgumentException.class, () -> statusService.deleteStatusById(100L));
    }
}
