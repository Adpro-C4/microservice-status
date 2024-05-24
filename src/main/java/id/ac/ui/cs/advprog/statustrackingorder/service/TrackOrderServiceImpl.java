package id.ac.ui.cs.advprog.statustrackingorder.service;

import id.ac.ui.cs.advprog.statustrackingorder.model.TrackOrder;
import id.ac.ui.cs.advprog.statustrackingorder.repository.TrackOrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
public class TrackOrderServiceImpl implements TrackOrderService {

    private final TrackOrderRepository trackOrderRepository;
    private static final String INVALID_INPUT_LENGTH = "Invalid input: '%s' must be equals 12.";
    private static final String INVALID_INPUT_NUMBER = "Invalid input: '%s' must be a number.";
    private static final Random RANDOM = new Random();
    private static final String REGEX = "\\d+";

    @Autowired
    public TrackOrderServiceImpl(TrackOrderRepository trackOrderRepository) {
        this.trackOrderRepository = trackOrderRepository;
    }


    @Override
    @Async
    public CompletableFuture<TrackOrder> createTrackingAsync(TrackOrder trackOrder) {
        trackOrder.setTrackingId(UUID.randomUUID().toString());
        trackOrder.setResiCode(generateResiCode(trackOrder.getMethode(), trackOrder.getResiCode()));
        TrackOrder savedTrackOrder = trackOrderRepository.save(trackOrder);
        return CompletableFuture.completedFuture(savedTrackOrder);
    }

    @Override
    public TrackOrder getTrackingById(String id) {
        Optional<TrackOrder> trackOrderOptional = trackOrderRepository.findById(id);
        return trackOrderOptional.orElseThrow(() -> new NoSuchElementException("No such track order with id: " + id));
    }

    @Override
    public TrackOrder getTrackingByOrderId(String orderId) {
        Optional<TrackOrder> trackOrderOptional = trackOrderRepository.findByOrderId(orderId);
        return trackOrderOptional.orElseThrow(() -> new NoSuchElementException("No such track order with order id: " + orderId));
    }

    String generateResiCode(String method, String resiCode) {
        return switch (method.toLowerCase()) {
            case "jte" -> "JTE-" + resiJTE(resiCode);
            case "gobek" -> "GBK-" + resiGBK(resiCode);
            case "siwuzz" -> "SWZ-" + resiSIWUZZ(resiCode);
            default -> throw new IllegalArgumentException("Invalid shipping method");
        };
    }

    String resiJTE(String resiCode) {
        if (!resiCode.equals("auto")) {
            return validateNumber(resiCode);
        }
        return genereteNumber();
    }

    String resiGBK(String resiCode) {
        if (!resiCode.equals("auto")) {
            return validateAlphanumeric(resiCode);
        }
        return generateAlphanumeric();
    }

    String resiSIWUZZ(String resiCode) {
        if (!resiCode.equals("auto")) {
            return validateAlphanumeric(resiCode);
        }
        return generateAlphanumericNonNumber();
    }

    String validateNumber(String resiCode) {
        if (resiCode.length() != 12) {
            throw new IllegalArgumentException(String.format(INVALID_INPUT_LENGTH, resiCode));
        }
        if (!resiCode.matches(REGEX)) {
            throw new IllegalArgumentException(String.format(INVALID_INPUT_NUMBER, resiCode));
        }
        return resiCode;
    }

    String validateAlphanumeric(String resiCode) {
        if (resiCode.length() != 12) {
            throw new IllegalArgumentException(String.format(INVALID_INPUT_LENGTH, resiCode));
        }
        return resiCode;
    }

    String genereteNumber() {
        LocalDateTime now = LocalDateTime.now();
        String year = String.format("%02d", now.getYear() % 100);
        String month = String.format("%02d", now.getMonthValue());
        String day = String.format("%02d", now.getDayOfMonth());
        String hour = String.format("%02d", now.getHour());
        String minute = String.format("%02d", now.getMinute());

        String generatedNumber = year + month + day + hour + minute;

        if (generatedNumber.length() < 12) {
            int remainingLength = 12 - generatedNumber.length();
            generatedNumber += generateRandomDigits(remainingLength);
        }

        return generatedNumber;
    }

    String generateAlphanumeric() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        StringBuilder sb = new StringBuilder(12);

        for (int i = 0; i < 12; i++) {
            char randomChar = characters.charAt(RANDOM.nextInt(characters.length()));
            sb.append(randomChar);
        }
        return sb.toString();
    }

    String generateAlphanumericNonNumber() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        StringBuilder sb = new StringBuilder(12);

        for (int i = 0; i < 12; i++) {
            char randomChar = characters.charAt(RANDOM.nextInt(characters.length()));
            sb.append(randomChar);
        }
        return sb.toString();
    }

    static String generateRandomDigits(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(RANDOM.nextInt(10));
        }
        return sb.toString();
    }
}
