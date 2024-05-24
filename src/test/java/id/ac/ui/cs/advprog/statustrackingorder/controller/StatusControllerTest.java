package id.ac.ui.cs.advprog.statustrackingorder.controller;

import id.ac.ui.cs.advprog.statustrackingorder.model.Status;
import id.ac.ui.cs.advprog.statustrackingorder.service.StatusService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StatusControllerTest {

    @Mock
    private StatusService statusService;

    @InjectMocks
    private StatusController statusController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetStatusById() {
        Status status = new Status();
        when(statusService.getStatusById(1L)).thenReturn(status);

        ResponseEntity<Object> response = statusController.getStatusById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(status, response.getBody());
    }

    @Test
    public void testGetStatusByIdNotFound() {
        when(statusService.getStatusById(1L)).thenThrow(new NoSuchElementException());

        ResponseEntity<Object> response = statusController.getStatusById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No such status with id: 1", response.getBody());
    }

    @Test
    public void testGetStatusByOrderId() {
        Status status = new Status();
        when(statusService.getStatusByOrderId("order123")).thenReturn(status);

        ResponseEntity<Object> response = statusController.getStatusByOderId("order123");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(status, response.getBody());
    }

    @Test
    public void testGetStatusByOrderIdNotFound() {
        when(statusService.getStatusByOrderId("order123")).thenThrow(new NoSuchElementException());

        ResponseEntity<Object> response = statusController.getStatusByOderId("order123");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No such status with id: order123", response.getBody());
    }

    @Test
    public void testAllStatus() {
        Status status1 = new Status();
        Status status2 = new Status();
        when(statusService.getAllStatus()).thenReturn(Arrays.asList(status1, status2));

        ResponseEntity<Object> response = statusController.allStatus();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Arrays.asList(status1, status2), response.getBody());
    }

    @Test
    public void testCreateStatus() {
        Status status = new Status();
        when(statusService.createStatusAsync(any(Status.class))).thenReturn(CompletableFuture.completedFuture(status));

        ResponseEntity<Object> response = statusController.createStatus(status);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Status created successfully", response.getBody());
        verify(statusService, times(1)).createStatusAsync(status);
    }

    @Test
    public void testUpdateStatus() {
        Status status = new Status();
        status.setId(1L);

        doNothing().when(statusService).updateStatus(status);

        ResponseEntity<Object> response = statusController.updateStatus(1L, status);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Status updated successfully", response.getBody());
        verify(statusService, times(1)).updateStatus(status);
    }

    @Test
    public void testUpdateStatusNotFound() {
        Status status = new Status();
        status.setId(1L);

        doThrow(new NoSuchElementException()).when(statusService).updateStatus(status);

        ResponseEntity<Object> response = statusController.updateStatus(1L, status);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(statusService, times(1)).updateStatus(status);
    }
}
