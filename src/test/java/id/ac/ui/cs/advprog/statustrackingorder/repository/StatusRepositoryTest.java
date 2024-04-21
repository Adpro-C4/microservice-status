package id.ac.ui.cs.advprog.statustrackingorder.repository;

import id.ac.ui.cs.advprog.statustrackingorder.model.Order;
import id.ac.ui.cs.advprog.statustrackingorder.model.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

public class StatusRepositoryTest {
    StatusRepository statusRepository;
    List<Status> allStatus;

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
        Status status1 = new Status(order1, "Invalidate");
        Status status2 = new Status(order2, "Invalidate");
        Status status3 = new Status(order3, "Invalidate");
        allStatus.add(status1);
        allStatus.add(status2);
        allStatus.add(status3);

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
        Status newStatus = new Status(allStatus.get(1).getOrder(), "Cancelled");
        Status result = statusRepository.save(newStatus);

        Status findResult = statusRepository.findById(allStatus.get(1).getId());
        Assertions.assertEquals(status.getId(), result.getId());
        Assertions.assertEquals(status.getId(), findResult.getId());
        Assertions.assertEquals(status.getOrderStatus(), findResult.getOrderStatus());
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

        Status findResult = statusRepository.findById("GAKADAID");
        Assertions.assertNull(findResult);
    }

    @Test
    public void testFindOrderStatusByIdWithNonexistentOrder() {
        Order order1 = new Order();
        order1.setOrderId(1L);
        order1.setOrderName("Order BMW");
        Status orderStatus1 = new Status(order1, "Verified");

        statusRepository.save(orderStatus1);
        Status foundStatus = statusRepository.findById(2L);

        Assertions.assertNull(foundStatus);
    }



}
