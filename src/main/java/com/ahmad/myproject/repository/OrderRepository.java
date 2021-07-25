package com.ahmad.myproject.repository;

import com.ahmad.myproject.entity.Order;
import com.ahmad.myproject.registeration.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findTransactionByUser(AppUser user);
}