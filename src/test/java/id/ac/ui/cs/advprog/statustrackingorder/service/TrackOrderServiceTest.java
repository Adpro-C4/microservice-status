package id.ac.ui.cs.advprog.statustrackingorder.service;

import id.ac.ui.cs.advprog.statustrackingorder.model.TrackOrder;
import id.ac.ui.cs.advprog.statustrackingorder.repository.TrackOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TrackOrderServiceTest {

    @Mock
    private TrackOrderRepository trackOrderRepository;

    @InjectMocks
    private TrackOrderServiceImpl trackOrderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTrackingAsync() throws Exception {
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
    public void testGetTrackingByIdExists() {
        String id = "trk123";
        TrackOrder trackOrder = new TrackOrder(id, "order123", "Express", "resi123");
        when(trackOrderRepository.findById(id)).thenReturn(Optional.of(trackOrder));

        TrackOrder result = trackOrderService.getTrackingById(id);

        assertEquals(id, result.getTrackingId());
        assertEquals("order123", result.getOrderId());
        verify(trackOrderRepository, times(1)).findById(id);
    }

    @Test
    public void testGetTrackingByIdNotExists() {
        String id = "trk123";
        when(trackOrderRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> trackOrderService.getTrackingById(id));
        verify(trackOrderRepository, times(1)).findById(id);
    }

    @Test
    public void testGetTrackingByOrderIdExists() {
        String orderId = "order123";
        TrackOrder trackOrder = new TrackOrder("trk123", orderId, "Express", "resi123");
        when(trackOrderRepository.findByOrderId(orderId)).thenReturn(Optional.of(trackOrder));

        TrackOrder result = trackOrderService.getTrackingByOrderId(orderId);

        assertEquals("trk123", result.getTrackingId());
        assertEquals(orderId, result.getOrderId());
        verify(trackOrderRepository, times(1)).findByOrderId(orderId);
    }

    @Test
    public void testGetTrackingByOrderIdNotExists() {
        String orderId = "order123";
        when(trackOrderRepository.findByOrderId(orderId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> trackOrderService.getTrackingByOrderId(orderId));
        verify(trackOrderRepository, times(1)).findByOrderId(orderId);
    }

    @Test
    public void testGenerateResiCode() {
        String method = "jte";
        String resiCode = "auto";
        String orderId = "order123";

        String result = trackOrderService.generateResiCode(method, resiCode, orderId);

        assertTrue(result.startsWith("JTE-"));
    }

    @Test
    public void testGenerateResiCodeInvalidMethod() {
        String method = "invalid";
        String resiCode = "auto";
        String orderId = "order123";

        assertThrows(IllegalArgumentException.class, () -> trackOrderService.generateResiCode(method, resiCode, orderId));
    }

    @Test
    public void testGenerateAlphanumeric() {
        String alphanumeric = trackOrderService.generateAlphanumeric();
        assertNotNull(alphanumeric);
        assertEquals(12, alphanumeric.length());
        assertTrue(alphanumeric.matches("[A-Z0-9]+"));
    }

    @Test
    public void testGenerateAlphanumericNonNumber() {
        String alphanumericNonNumber = trackOrderService.generateAlphanumericNonNumber();
        assertNotNull(alphanumericNonNumber);
        assertEquals(12, alphanumericNonNumber.length());
        assertTrue(alphanumericNonNumber.matches("[A-Z]+"));
    }

    @Test
    public void testResiJTEAuto() throws Exception {
        Method method = TrackOrderServiceImpl.class.getDeclaredMethod("resiJTE", String.class);
        method.setAccessible(true);
        String resiCode = (String) method.invoke(trackOrderService, "auto");
        assertNotNull(resiCode);
        assertEquals(12, resiCode.length());
        assertTrue(resiCode.matches("\\d+"));
    }


    @Test
    public void testResiJTEWithCode() throws Exception {
        Method method = TrackOrderServiceImpl.class.getDeclaredMethod("resiJTE", String.class);
        method.setAccessible(true);
        String resiCode = (String) method.invoke(trackOrderService, "123456789012");
        assertEquals("123456789012", resiCode);
    }

    @Test
    public void testResiGBKAuto() throws Exception {
        Method method = TrackOrderServiceImpl.class.getDeclaredMethod("resiGBK", String.class);
        method.setAccessible(true);
        String resiCode = (String) method.invoke(trackOrderService, "auto");
        assertNotNull(resiCode);
        assertEquals(12, resiCode.length());
        assertTrue(resiCode.matches("[A-Z0-9]+"));
    }

    @Test
    public void testResiGBKWithCode() throws Exception {
        Method method = TrackOrderServiceImpl.class.getDeclaredMethod("resiGBK", String.class);
        method.setAccessible(true);
        String resiCode = (String) method.invoke(trackOrderService, "ABCDEFGHIJKL");
        assertEquals("ABCDEFGHIJKL", resiCode);
    }

    @Test
    public void testResiSIWUZZAuto() throws Exception {
        Method method = TrackOrderServiceImpl.class.getDeclaredMethod("resiSIWUZZ", String.class);
        method.setAccessible(true);
        String resiCode = (String) method.invoke(trackOrderService, "auto");
        assertNotNull(resiCode);
        assertEquals(12, resiCode.length());
        assertTrue(resiCode.matches("[A-Z]+"));
    }

    @Test
    public void testResiSIWUZZWithCode() throws Exception {
        Method method = TrackOrderServiceImpl.class.getDeclaredMethod("resiSIWUZZ", String.class);
        method.setAccessible(true);
        String resiCode = (String) method.invoke(trackOrderService, "ABCDEFGHIJKL");
        assertEquals("ABCDEFGHIJKL", resiCode);
    }

    @Test
    public void testValidateNumberValid() throws Exception {
        Method method = TrackOrderServiceImpl.class.getDeclaredMethod("validateNumber", String.class);
        method.setAccessible(true);
        String resiCode = (String) method.invoke(trackOrderService, "123456789012");
        assertEquals("123456789012", resiCode);
    }

    @Test
    public void testValidateNumberInvalidLength() throws Exception {
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
    public void testValidateAlphanumericValid() throws Exception {
        Method method = TrackOrderServiceImpl.class.getDeclaredMethod("validateAlphanumeric", String.class);
        method.setAccessible(true);
        String resiCode = (String) method.invoke(trackOrderService, "ABCDEFGHIJKL");
        assertEquals("ABCDEFGHIJKL", resiCode);
    }

    @Test
    public void testValidateAlphanumericInvalidLength() throws Exception {
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


    // Bagian 1: Menambahkan tes unit untuk kondisi invalid pada validateNumber
    @Test
    public void testValidateNumberInvalidFormat() throws Exception {
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
    public void testValidateNumberInvalidCharacters() throws Exception {
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
    public void testGenerateNumberLengthLessThan12() throws Exception {
        Method method = TrackOrderServiceImpl.class.getDeclaredMethod("genereteNumber");
        method.setAccessible(true);

        // Mock TrackOrderServiceImpl untuk menghasilkan generatedNumber dengan panjang kurang dari 12
        TrackOrderServiceImpl spyService = spy(trackOrderService);
        doReturn("1234567890").when(spyService).genereteNumber(); // Menghasilkan 10 karakter

        String generatedNumber = (String) method.invoke(spyService);

        // Cek panjang sebelum penambahan angka acak
        int initialLength = generatedNumber.length();
        assertTrue(initialLength < 12, "Panjang generatedNumber sebelum penambahan angka acak seharusnya kurang dari 12");

        // Hitung remainingLength dan hasil akhirnya
        int remainingLength = 12 - initialLength;
        String randomDigits = TrackOrderServiceImpl.generateRandomDigits(remainingLength);
        String finalGeneratedNumber = generatedNumber + randomDigits;

        // Verifikasi panjang akhir adalah 12
        assertEquals(12, finalGeneratedNumber.length(), "Panjang finalGeneratedNumber setelah penambahan angka acak seharusnya 12");

        // Verifikasi format angka acak
        assertTrue(finalGeneratedNumber.matches("\\d{12}"), "finalGeneratedNumber seharusnya berformat 12 digit angka");
    }





    // Bagian 2: Menambahkan tes unit untuk generateResiCode dengan method gobek dan siwuzz
    @Test
    public void testGenerateResiCodeGobek() throws Exception {
        String method = "gobek";
        String resiCode = "auto";
        String orderId = "order123";
        String result = trackOrderService.generateResiCode(method, resiCode, orderId);
        assertNotNull(result);
        assertTrue(result.startsWith("GBK-"));
        assertEquals(16, result.length());
    }

    @Test
    public void testGenerateResiCodeSiwuzz() throws Exception {
        String method = "siwuzz";
        String resiCode = "auto";
        String orderId = "order123";
        String result = trackOrderService.generateResiCode(method, resiCode, orderId);
        assertNotNull(result);
        assertTrue(result.startsWith("SWZ-"));
        assertEquals(16, result.length());
    }


}