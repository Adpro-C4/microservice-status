package id.ac.ui.cs.advprog.statustrackingorder.service;

import id.ac.ui.cs.advprog.statustrackingorder.model.TrackOrder;
import id.ac.ui.cs.advprog.statustrackingorder.repository.TrackOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TrackOrderServiceTest {

    @Mock
    private TrackOrderRepository trackOrderRepository;

    @InjectMocks
    private TrackOrderServiceImpl trackOrderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void testCreateTrackingAsync() throws Exception {
        TrackOrder trackOrder = new TrackOrder();
        trackOrder.setOrderId("order123");
        trackOrder.setMethode("jte");
        trackOrder.setResiCode("auto");

        TrackOrder savedTrackOrder = new TrackOrder(UUID.randomUUID().toString(), "order123", "jte", "JTE-123456789012");
        when(trackOrderRepository.save(any(TrackOrder.class))).thenReturn(savedTrackOrder);

        CompletableFuture<TrackOrder> future = trackOrderService.createTrackingAsync(trackOrder);
        TrackOrder result = future.get();

        assertNotNull(result.getTrackingId());
        assertEquals("order123", result.getOrderId());
        assertEquals("jte", result.getMethode());
        assertTrue(result.getResiCode().startsWith("JTE-"));
        verify(trackOrderRepository, times(1)).save(any(TrackOrder.class));
    }

    @Test
    void testGetTrackingByIdExists() {
        String id = "trk123";
        TrackOrder trackOrder = new TrackOrder(id, "order123", "Express", "resi123");
        when(trackOrderRepository.findById(id)).thenReturn(Optional.of(trackOrder));

        TrackOrder result = trackOrderService.getTrackingById(id);

        assertEquals(id, result.getTrackingId());
        assertEquals("order123", result.getOrderId());
        verify(trackOrderRepository, times(1)).findById(id);
    }

    @Test
    void testGetTrackingByIdNotExists() {
        String id = "trk123";
        when(trackOrderRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> trackOrderService.getTrackingById(id));
        verify(trackOrderRepository, times(1)).findById(id);
    }

    @Test
    void testGetTrackingByOrderIdExists() {
        String orderId = "order123";
        TrackOrder trackOrder = new TrackOrder("trk123", orderId, "Express", "resi123");
        when(trackOrderRepository.findByOrderId(orderId)).thenReturn(Optional.of(trackOrder));

        TrackOrder result = trackOrderService.getTrackingByOrderId(orderId);

        assertEquals("trk123", result.getTrackingId());
        assertEquals(orderId, result.getOrderId());
        verify(trackOrderRepository, times(1)).findByOrderId(orderId);
    }

    @Test
    void testGetTrackingByOrderIdNotExists() {
        String orderId = "order123";
        when(trackOrderRepository.findByOrderId(orderId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> trackOrderService.getTrackingByOrderId(orderId));
        verify(trackOrderRepository, times(1)).findByOrderId(orderId);
    }

    @Test
    void testGenerateResiCode() {
        String method = "jte";
        String resiCode = "auto";

        String result = trackOrderService.generateResiCode(method, resiCode);

        assertTrue(result.startsWith("JTE-"));
    }
    @Test
    void testGenerateResiCodeInvalidMethod() {
        String method = "invalid";
        String resiCode = "auto";

        assertThrows(IllegalArgumentException.class, () -> trackOrderService.generateResiCode(method, resiCode));
    }
    @Test
    void testGenerateAlphanumeric() {
        String alphanumeric = trackOrderService.generateAlphanumeric();
        assertNotNull(alphanumeric);
        assertEquals(12, alphanumeric.length());
        assertTrue(alphanumeric.matches("[A-Z0-9]+"));
    }
    @Test
    void testGenerateAlphanumericNonNumber() {
        String alphanumericNonNumber = trackOrderService.generateAlphanumericNonNumber();
        assertNotNull(alphanumericNonNumber);
        assertEquals(12, alphanumericNonNumber.length());
        assertTrue(alphanumericNonNumber.matches("[A-Z]+"));
    }
    @Test
    void testResiJTEAuto() throws Exception {
        Method method = TrackOrderServiceImpl.class.getDeclaredMethod("resiJTE", String.class);
        method.setAccessible(true);
        String resiCode = (String) method.invoke(trackOrderService, "auto");
        assertNotNull(resiCode);
        assertEquals(12, resiCode.length());
        assertTrue(resiCode.matches("\\d+"));
    }
    @Test
    void testResiJTEWithCode() throws Exception {
        Method method = TrackOrderServiceImpl.class.getDeclaredMethod("resiJTE", String.class);
        method.setAccessible(true);
        String resiCode = (String) method.invoke(trackOrderService, "123456789012");
        assertEquals("123456789012", resiCode);
    }
    @Test
    void testResiGBKAuto() throws Exception {
        Method method = TrackOrderServiceImpl.class.getDeclaredMethod("resiGBK", String.class);
        method.setAccessible(true);
        String resiCode = (String) method.invoke(trackOrderService, "auto");
        assertNotNull(resiCode);
        assertEquals(12, resiCode.length());
        assertTrue(resiCode.matches("[A-Z0-9]+"));
    }
    @Test
    void testResiGBKWithCode() throws Exception {
        Method method = TrackOrderServiceImpl.class.getDeclaredMethod("resiGBK", String.class);
        method.setAccessible(true);
        String resiCode = (String) method.invoke(trackOrderService, "ABCDEFGHIJKL");
        assertEquals("ABCDEFGHIJKL", resiCode);
    }
    @Test
    void testResiSIWUZZAuto() throws Exception {
        Method method = TrackOrderServiceImpl.class.getDeclaredMethod("resiSIWUZZ", String.class);
        method.setAccessible(true);
        String resiCode = (String) method.invoke(trackOrderService, "auto");
        assertNotNull(resiCode);
        assertEquals(12, resiCode.length());
        assertTrue(resiCode.matches("[A-Z]+"));
    }
    @Test
    void testResiSIWUZZWithCode() throws Exception {
        Method method = TrackOrderServiceImpl.class.getDeclaredMethod("resiSIWUZZ", String.class);
        method.setAccessible(true);
        String resiCode = (String) method.invoke(trackOrderService, "ABCDEFGHIJKL");
        assertEquals("ABCDEFGHIJKL", resiCode);
    }
    @Test
    void testValidateNumberValid() throws Exception {
        Method method = TrackOrderServiceImpl.class.getDeclaredMethod("validateNumber", String.class);
        method.setAccessible(true);
        String resiCode = (String) method.invoke(trackOrderService, "123456789012");
        assertEquals("123456789012", resiCode);
    }
    @Test
    void testValidateNumberInvalidLength() throws Exception {
        Method method = TrackOrderServiceImpl.class.getDeclaredMethod("validateNumber", String.class);
        method.setAccessible(true);
        try {
            method.invoke(trackOrderService, "12345");
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (InvocationTargetException e) {
            Throwable actualException = e.getCause();
            assertTrue(actualException instanceof IllegalArgumentException);
            assertEquals("Invalid input: '12345' must be equals 12.", actualException.getMessage());
        }
    }

    @Test
    void testValidateAlphanumericValid() throws Exception {
        Method method = TrackOrderServiceImpl.class.getDeclaredMethod("validateAlphanumeric", String.class);
        method.setAccessible(true);
        String resiCode = (String) method.invoke(trackOrderService, "ABCDEFGHIJKL");
        assertEquals("ABCDEFGHIJKL", resiCode);
    }

    @Test
    void testValidateAlphanumericInvalidLength() throws Exception {
        Method method = TrackOrderServiceImpl.class.getDeclaredMethod("validateAlphanumeric", String.class);
        method.setAccessible(true);
        try {
            method.invoke(trackOrderService, "ABCDE");
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (InvocationTargetException e) {
            Throwable actualException = e.getCause();
            assertTrue(actualException instanceof IllegalArgumentException);
            assertEquals("Invalid input: 'ABCDE' must be equals 12.", actualException.getMessage());
        }
    }

    @Test
    void testValidateNumberInvalidFormat() throws Exception {
        Method method = TrackOrderServiceImpl.class.getDeclaredMethod("validateNumber", String.class);
        method.setAccessible(true);
        try {
            method.invoke(trackOrderService, "12345ABCDE");
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (InvocationTargetException e) {
            Throwable actualException = e.getCause();
            assertTrue(actualException instanceof IllegalArgumentException);
            assertEquals("Invalid input: '12345ABCDE' must be equals 12.", actualException.getMessage());
        }
    }

    @Test
    void testValidateNumberInvalidCharacters() throws Exception {
        Method method = TrackOrderServiceImpl.class.getDeclaredMethod("validateNumber", String.class);
        method.setAccessible(true);
        try {
            method.invoke(trackOrderService, "12345ABCDE67");
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (InvocationTargetException e) {
            Throwable actualException = e.getCause();
            assertTrue(actualException instanceof IllegalArgumentException);
            assertEquals("Invalid input: '12345ABCDE67' must be a number.", actualException.getMessage());
        }
    }

    @Test
    void testGenerateNumberLengthLessThan12() throws Exception {
        Method method = TrackOrderServiceImpl.class.getDeclaredMethod("genereteNumber");
        method.setAccessible(true);

        TrackOrderServiceImpl spyService = spy(trackOrderService);
        doReturn("1234567890").when(spyService).genereteNumber();

        String generatedNumber = (String) method.invoke(spyService);

        int initialLength = generatedNumber.length();
        assertTrue(initialLength < 12, "Panjang generatedNumber sebelum penambahan angka acak seharusnya kurang dari 12");

        int remainingLength = 12 - initialLength;
        String randomDigits = TrackOrderServiceImpl.generateRandomDigits(remainingLength);
        String finalGeneratedNumber = generatedNumber + randomDigits;

        assertEquals(12, finalGeneratedNumber.length(), "Panjang finalGeneratedNumber setelah penambahan angka acak seharusnya 12");

        assertTrue(finalGeneratedNumber.matches("\\d{12}"), "finalGeneratedNumber seharusnya berformat 12 digit angka");
    }

    @Test
    void testGenerateResiCodeGobek() throws Exception {
        String method = "gobek";
        String resiCode = "auto";

        String result = trackOrderService.generateResiCode(method, resiCode);
        assertNotNull(result);
        assertTrue(result.startsWith("GBK-"));
        assertEquals(16, result.length());
    }

    @Test
    void testGenerateResiCodeSiwuzz() throws Exception {
        String method = "siwuzz";
        String resiCode = "auto";

        String result = trackOrderService.generateResiCode(method, resiCode);
        assertNotNull(result);
        assertTrue(result.startsWith("SWZ-"));
        assertEquals(16, result.length());
    }


}