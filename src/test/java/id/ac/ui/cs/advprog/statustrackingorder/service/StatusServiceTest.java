package id.ac.ui.cs.advprog.statustrackingorder.service;

import id.ac.ui.cs.advprog.statustrackingorder.enums.OrderStatus;
import id.ac.ui.cs.advprog.statustrackingorder.eventdriven.Producer;
import id.ac.ui.cs.advprog.statustrackingorder.model.Status;
import id.ac.ui.cs.advprog.statustrackingorder.repository.StatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StatusServiceTest {

    @Mock
    private StatusRepository statusRepository;

    @Mock
    private Producer messageQueue;

    @InjectMocks
    private StatusServiceImpl statusService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateStatusAsync() throws Exception {
        Status status = new Status();
        when(statusRepository.save(status)).thenReturn(status);

        CompletableFuture<Status> future = statusService.createStatusAsync(status);
        Status result = future.get();

        assertEquals(status, result);
        verify(statusRepository, times(1)).save(status);
    }

    @Test
    void testGetStatusById() {
        Status status = new Status();
        when(statusRepository.findById(1L)).thenReturn(Optional.of(status));

        Status result = statusService.getStatusById(1L);

        assertEquals(status, result);
    }

    @Test
    void testGetStatusByIdNotFound() {
        when(statusRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            statusService.getStatusById(1L);
        });
    }

    @Test
    void testGetStatusByOrderId() {
        Status status = new Status();
        when(statusRepository.findByOrderId("order123")).thenReturn(Optional.of(status));

        Status result = statusService.getStatusByOrderId("order123");

        assertEquals(status, result);
    }

    @Test
    void testGetStatusByOrderIdNotFound() {
        when(statusRepository.findByOrderId("order123")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            statusService.getStatusByOrderId("order123");
        });
    }

    @Test
    void testUpdateStatus() throws Exception {
        Status oldStatus = new Status();
        oldStatus.setId(1L);
        oldStatus.setOrderStatus(OrderStatus.MENUNGGU_PERSETUJUAN_ADMIN.getDisplayName());
        oldStatus.setOrderId("order123");

        Status newStatus = new Status();
        newStatus.setId(1L);
        newStatus.setOrderStatus(OrderStatus.SELESAI.getDisplayName());

        when(statusRepository.findById(1L)).thenReturn(Optional.of(oldStatus));

        statusService.updateStatus(newStatus);

        verify(statusRepository, times(1)).findById(1L);
        verify(statusRepository, times(1)).save(oldStatus);
        verify(messageQueue, times(1)).sendMessage("update-stok-produk-routing-key", "order123");
        assertEquals(OrderStatus.SELESAI.getDisplayName(), oldStatus.getOrderStatus());
    }

    @Test
    void testUpdateStatusToCompletedNotSendMessage() throws Exception {
        Status oldStatus = new Status();
        oldStatus.setId(1L);
        oldStatus.setOrderStatus(OrderStatus.SELESAI.getDisplayName());
        oldStatus.setOrderId("order123");

        Status newStatus = new Status();
        newStatus.setId(1L);
        newStatus.setOrderStatus(OrderStatus.GAGAL.getDisplayName());

        when(statusRepository.findById(1L)).thenReturn(Optional.of(oldStatus));

        statusService.updateStatus(newStatus);

        verify(statusRepository, times(1)).findById(1L);
        verify(statusRepository, times(1)).save(oldStatus);
        verify(messageQueue, times(0)).sendMessage(anyString(), anyString());
        assertEquals(OrderStatus.SELESAI.getDisplayName(), oldStatus.getOrderStatus());
    }

    @Test
    void testUpdateStatusNotFound() {
        Status newStatus = new Status();
        newStatus.setId(1L);
        newStatus.setOrderStatus(OrderStatus.SELESAI.getDisplayName());

        when(statusRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            statusService.updateStatus(newStatus);
        });
    }

    @Test
    void testGetAllStatus() {
        Status status1 = new Status();
        Status status2 = new Status();
        when(statusRepository.findAll()).thenReturn(Arrays.asList(status1, status2));

        List<Status> result = statusService.getAllStatus();

        assertEquals(2, result.size());
        assertTrue(result.contains(status1));
        assertTrue(result.contains(status2));
    }
}
