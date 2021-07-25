package com.ahmad.myproject.service;

import com.ahmad.myproject.entity.Order;
import com.ahmad.myproject.registeration.appuser.AppUser;
import com.ahmad.myproject.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    @Autowired
    public void setTransactionRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order create(Order order){
        return orderRepository.save(order);
    }
    public List<Order> findByUser(AppUser user){
        return orderRepository.findTransactionByUser(user);
    }
    public Order findById(long id){
        Optional<Order> transaction = orderRepository.findById(id);
        if(transaction.isPresent()){
            return  transaction.get();
        }
        throw new IllegalStateException("Invalid id");
    }
    public void DeleteById(long id){
        orderRepository.deleteById(id);
    }
    public List<Order> findAll(){
        return orderRepository.findAll();
    }
}