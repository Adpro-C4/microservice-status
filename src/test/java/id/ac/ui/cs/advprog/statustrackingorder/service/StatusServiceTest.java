package id.ac.ui.cs.advprog.statustrackingorder.service;

import id.ac.ui.cs.advprog.statustrackingorder.model.Order;
import id.ac.ui.cs.advprog.statustrackingorder.model.Status;
import id.ac.ui.cs.advprog.statustrackingorder.repository.StatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class StatusServiceTests {

    @Mock
    private StatusRepository statusRepository;

    private StatusService statusService;

    @BeforeEach
    void setUp() {
        statusService = new StatusServiceImpl(statusRepository);
    }

    @Test
    void testCreateStatus() {
        Order order1 = new Order();
        order1.setOrderId(1L);
        order1.setOrderName("Order BMW");

        String orderStatus = "Verified";

        Status expectedStatus = Status.createStatus(order1, orderStatus);

        when(statusRepository.save(expectedStatus)).thenReturn(expectedStatus);

        Status actualStatus = statusService.createStatus(order1, orderStatus);

        assertEquals(expectedStatus, actualStatus);
        verify(statusRepository).save(expectedStatus);
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

    //,.,.
    @Test
    void testCreateStatusWithExistingId() {
        Order order = new Order();
        order.setOrderId(1L);
        order.setOrderName("Order BMW");
        String orderStatus = "Verified";
        Status existingStatus = new Status(1L, order, orderStatus);

        when(statusRepository.findById(1L)).thenReturn(existingStatus);
        assertThrows(IllegalArgumentException.class, () -> statusService.createStatus(order, orderStatus));
    }

    // Test tambahan untuk findStatusById

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
    void testFindStatusByIdWithNonExistingId() {
        assertThrows(IllegalArgumentException.class, () -> statusService.findStatusById(100L));
    }

    // Test tambahan untuk updateStatus

    @Test
    void testUpdateStatusWithExistingStatus() {
        Order order = new Order();
        order.setOrderId(1L);
        order.setOrderName("Order BMW");

        String orderStatus = "Cancelled";
        Status existingStatus = new Status(1L, order, "Unverified");
        Status updatedStatus = new Status(1L, order, orderStatus);

        when(statusRepository.findById(1L)).thenReturn(existingStatus);
        when(statusRepository.update(updatedStatus)).thenReturn(updatedStatus);

        Status result = statusService.updateStatus(updatedStatus);

        assertEquals(updatedStatus, result);
        verify(statusRepository).findById(1L);
        verify(statusRepository).update(updatedStatus);
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

    // Test tambahan untuk deleteStatusById

    @Test
    void testDeleteStatusByIdWithExistingId() {
        Order order = new Order();
        order.setOrderId(1L);
        order.setOrderName("Order BMW");

        when(statusRepository.findById(1L)).thenReturn(new Status(1L, order, "Verified"));

        statusService.deleteStatusById(1L);

        verify(statusRepository).findById(1L);
        verify(statusRepository).deleteById(1L);
    }

    @Test
    void testDeleteStatusByIdWithNonExistingId() {
        assertThrows(IllegalArgumentException.class, () -> statusService.deleteStatusById(100L));
    }
}
