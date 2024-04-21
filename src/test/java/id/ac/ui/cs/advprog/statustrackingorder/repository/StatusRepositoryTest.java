package id.ac.ui.cs.advprog.statustrackingorder.repository;

import id.ac.ui.cs.advprog.statustrackingorder.model.Order;
import id.ac.ui.cs.advprog.statustrackingorder.model.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatusRepositoryTest {
    StatusRepository statusRepository;
    List<Status> allStatus;
    Map<Long, Status> mapStatus;




    @BeforeEach
    void setUp() {
        statusRepository = new StatusRepository();


        Order order1 = new Order();
        order1.setOrderId(1L);
        order1.setOrderName("Memesan Kopi");

        Order order2 = new Order();
        order2.setOrderId(2L);
        order2.setOrderName("Memesan Teh");

        Order order3 = new Order();
        order3.setOrderId(3L);
        order3.setOrderName("Memesan Susu");

        allStatus = new ArrayList<>();

        Status status1 = new Status(order1, "Unverified");
        Status status2 = new Status(order2, "Unverified");
        Status status3 = new Status(order3, "Unverified");

        Order order4 = new Order();
        order4.setOrderId(4L);
        order4.setOrderName("Memesan Jus");


        Order order5 = new Order();
        order5.setOrderId(5L);
        order5.setOrderName("Memesan Air Putih");

        Status status4 = new Status(4L, order4, "Unverified");
        Status status5 = new Status(5L, order5, "Unverified");

        allStatus.add(status1);
        allStatus.add(status2);
        allStatus.add(status3);

        mapStatus = new HashMap<>();
        mapStatus.put(status4.getId(), status1);
        mapStatus.put(status2.getId(), status2);
        mapStatus.put(status3.getId(), status3);



    }

    @Test
    void testSaveCreate(){
        Status status = allStatus.get(1);

        Status result = statusRepository.save(status);

        Status findResult = statusRepository.findById(allStatus.get(1).getId());

        Assertions.assertEquals(status.getId(), result.getId());

        Assertions.assertEquals(status.getId(), findResult.getId());
        Assertions.assertEquals(status.getOrderStatus(), findResult.getOrderStatus());

    }
    @Test
    void testSaveUpdate(){
        Status status = allStatus.get(1);
        statusRepository.save(status);

        // Menggunakan id yang sama dengan status yang sudah ada
        Status newStatus = new Status(allStatus.get(1).getOrder(), "Cancelled");
        newStatus.setId(status.getId());

        Status result = statusRepository.save(newStatus);

        Status findResult = statusRepository.findById(allStatus.get(1).getId());
        Assertions.assertEquals(newStatus.getId(), result.getId());
        Assertions.assertEquals(newStatus.getId(), findResult.getId());
        Assertions.assertEquals(newStatus.getOrderStatus(), findResult.getOrderStatus());
    }


    @Test
    void testFindByIdIfIdFound(){
        for (Status status: allStatus){
            statusRepository.save(status);
        }

        Status findResult = statusRepository.findById(allStatus.get(1).getId());
        Assertions.assertEquals(allStatus.get(1).getId(), findResult.getId());
        Assertions.assertEquals(allStatus.get(1).getOrderStatus(), findResult.getOrderStatus());
    }

    @Test
    void testFindByIdIfIdNotFound(){
        for (Status status: allStatus){
            statusRepository.save(status);
        }

        Status findResult = statusRepository.findById(909090L);
        Assertions.assertNull(findResult);
    }

    @Test
    void testFindOrderStatusByIdWithNonexistentOrder() {
        Order order1 = new Order();
        order1.setOrderId(1L);
        order1.setOrderName("Order BMW");
        Status orderStatus1 = new Status(order1, "Verified");

        statusRepository.save(orderStatus1);
        Status foundStatus = statusRepository.findById(2L);

        Assertions.assertNull(foundStatus);
    }
//
//
//    @Test
//    void testUpdateExistingStatus() {
//        // Arrange
//        Status existingStatus = mapStatus.get(4L);
//        Status updatedStatus = new Status(existingStatus.getOrder(), "Cancelled");
//        updatedStatus.setId(existingStatus.getId());
//
//        // Act
//        statusRepository.update(updatedStatus);
//
//        // Assert
//        Status retrievedStatus = statusRepository.findById(existingStatus.getId());
//        Assertions.assertEquals("Cancelled", retrievedStatus.getOrderStatus());
//    }



}
