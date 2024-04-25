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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class StatusControllerTest {

    @Mock
    private StatusService statusService;

    @InjectMocks
    private StatusController statusController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateStatus() {
        Status status = new Status();
        // Set up mock behavior
        when(statusService.createStatus(any(), any())).thenReturn(status);

        ResponseEntity<Status> response = statusController.createStatus(status);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(status, response.getBody());

        // Verify that the service method was called
        verify(statusService, times(1)).createStatus(any(), any());
    }

    @Test
    public void testFindStatusById() {
        Long id = 1L;
        Status status = new Status();
        // Set up mock behavior
        when(statusService.findStatusById(id)).thenReturn(status);

        ResponseEntity<Status> response = statusController.findStatusById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(status, response.getBody());

        // Verify that the service method was called
        verify(statusService, times(1)).findStatusById(id);
    }

    @Test
    public void testUpdateStatus() {
        Status status = new Status();
        // Set up mock behavior
        when(statusService.updateStatus(any())).thenReturn(status);

        ResponseEntity<Status> response = statusController.updateStatus(status);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(status, response.getBody());

        // Verify that the service method was called
        verify(statusService, times(1)).updateStatus(any());
    }

    @Test
    public void testDeleteStatusById() {
        Long id = 1L;
        // Set up mock behavior
        doNothing().when(statusService).deleteStatusById(id);

        ResponseEntity<Void> response = statusController.deleteStatusById(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        // Verify that the service method was called
        verify(statusService, times(1)).deleteStatusById(id);
    }

    // Uncomment and add more test methods as needed for other controller endpoints
}
