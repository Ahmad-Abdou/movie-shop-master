package com.ahmad.myproject.entity;

import com.ahmad.myproject.registeration.appuser.AppUser;
import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNumber;
    private double paymentAmount;
    private String paymentStatus;
    private LocalDate orderDate;
    @ManyToOne
    private AppUser user;
}

