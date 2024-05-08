package id.ac.ui.cs.advprog.statustrackingorder.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.ac.ui.cs.advprog.statustrackingorder.eventdriven.Producer;

@RestController
@RequestMapping("/test")
public class testrabbitmq {
    @Autowired
    Producer producer;

    @GetMapping("/rabbit-test")
    public String testhehe(){
        System.out.println("HAH");
        producer.sendMessage("purchase-routing-key","purchase-status/order-invalid");
        producer.sendMessage("order-status-routing-key","order-status/order-invalid");
        producer.sendMessage("tracking-order-routing-key", "hehe");
        return "DONE";
    }
}
