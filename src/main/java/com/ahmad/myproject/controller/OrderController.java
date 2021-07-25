package com.ahmad.myproject.controller;

import com.ahmad.myproject.entity.Order;
import com.ahmad.myproject.registeration.appuser.AppUser;
import com.ahmad.myproject.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/order")
@CrossOrigin("*")
public class OrderController {

    private OrderRepository orderRepository;

    @Autowired
    public void setTransactionRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody Order order){
        return ResponseEntity.status(HttpStatus.CREATED).body( orderRepository.save(order));
    }

    @GetMapping
    public ResponseEntity<List<Order>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body( orderRepository.findAll());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id){
        orderRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Order> findById(@PathVariable("id") Long id){
        Optional<Order> transaction = orderRepository.findById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(transaction.get());
    }
    @GetMapping("/user")
    public ResponseEntity<List<Order>> findByUser(@RequestParam("user")AppUser user){
        List<Order> order = orderRepository.findTransactionByUser(user);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(order);
    }

    @GetMapping("/ordernumber")
    public ResponseEntity<List<String>> getOrderNumber(){
      List<String> orderNumbers = orderRepository.findAll().stream().map(order -> order.getOrderNumber()).collect(Collectors.toList());
      return ResponseEntity.status(HttpStatus.OK).body(orderNumbers);
    }
}