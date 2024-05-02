package id.ac.ui.cs.advprog.statustrackingorder.service;

import id.ac.ui.cs.advprog.statustrackingorder.model.TrackOrder;
import id.ac.ui.cs.advprog.statustrackingorder.repository.TrackOrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
public class TrackOrderServiceImpl implements  TrackOrderService {

    @Autowired
    private  TrackOrderRepository trackOrderRepository;

    @Override
    public TrackOrder createTracking(TrackOrder trackOrder) {
        trackOrder.setResiCode(generateResiCode(trackOrder.getMethode(), trackOrder.getResiCode(), trackOrder.getOrderId()));
        return  trackOrderRepository.save(trackOrder);
    }

    @Override
    public TrackOrder getTrackingById(Long id) {
        Optional<TrackOrder> trackOrderOptional = trackOrderRepository.findById(id);
        if (trackOrderOptional.isPresent()) {
            TrackOrder tracking = trackOrderOptional.get();
            return tracking;
        } else {
            throw new NoSuchElementException("No such product with id: " + id);
        }
    }

    @Override
    public TrackOrder getTrackingByOrderId(Long orderId) {
        Optional<TrackOrder> trackOrderOptional = trackOrderRepository.findByOrderId(orderId);
        if (trackOrderOptional.isPresent()) {
            return trackOrderOptional.get();
        } else {
            throw new NoSuchElementException("No such product with id: " + orderId);
        }
    }
    private String generateResiCode(String method, String resiCode, Long orderId) {
            return switch (method.toLowerCase()) {
                case "jte" -> "JTE-" + resiJTE(resiCode) ;
                case "gobek" -> "GBK-" + resiGBK(resiCode);
                case "siwuzz" -> "SWZ-" + resiSIWUZZ(resiCode);
                default -> throw new IllegalArgumentException("Invalid shipping method");
            };
    }

    private String resiJTE(String resiCode) {
        if(!resiCode.equals("auto")){
            return validateNumber(resiCode);
        }
        return genereteNumber();
    }

    private String resiGBK(String resiCode) {
        if(!resiCode.equals("auto")){
            return validateAlphanumeric(resiCode);
        }
        return generateAlphanumeric();
    }

    private String resiSIWUZZ(String resiCode) {
        if(!resiCode.equals("auto")){
            return validateAlphanumeric(resiCode);
        }
        return generateAlphanumericNonNumber();
    }

    private String validateNumber(String resiCode) {
        if (resiCode.length() != 12){
            throw new IllegalArgumentException("Invalid input: '" + resiCode + "' must be equals 12.");
        }
        if (!resiCode.matches("\\d+")) {
            throw new IllegalArgumentException("Invalid input: '" + resiCode + "' must be a number.");
        }
        return resiCode;
    }

    private String validateAlphanumeric(String resiCode){
        if (resiCode.length() != 12){
            throw new IllegalArgumentException("Invalid input: '" + resiCode + "' must be equals 12.");
        }
        return resiCode;
    }

    private String genereteNumber(){
        LocalDateTime now = LocalDateTime.now();
        String year = String.format("%02d", now.getYear() % 100);
        String month = String.format("%02d", now.getMonthValue());
        String day = String.format("%02d", now.getDayOfMonth());
        String hour = String.format("%02d", now.getHour());
        String minute = String.format("%02d", now.getMinute());

        // Gabungkan semua bagian menjadi satu string
        String generatedNumber = year + month + day + hour + minute;

        // Periksa panjang string, jika kurang dari 12, tambahkan angka random
        if (generatedNumber.length() < 12) {
            int remainingLength = 12 - generatedNumber.length();
            generatedNumber += generateRandomDigits(remainingLength);
        }

        return generatedNumber;
    }

    private String generateAlphanumeric(){
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        Random random = new Random();
        StringBuilder sb = new StringBuilder(12);

        for (int i = 0; i < 12; i++) {
            char randomChar = characters.charAt(random.nextInt(characters.length()));
            sb.append(randomChar);
        }
        return sb.toString();
    }

    private String generateAlphanumericNonNumber(){
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        Random random = new Random();
        StringBuilder sb = new StringBuilder(12);

        for (int i = 0; i < 12; i++) {
            char randomChar = characters.charAt(random.nextInt(characters.length()));
            sb.append(randomChar);
        }
        return sb.toString();
    }

    private static String generateRandomDigits(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for(int i = 0; i < length; i++) {
            sb.append(random.nextInt(10)); //
        }
        return sb.toString();
    }


}
