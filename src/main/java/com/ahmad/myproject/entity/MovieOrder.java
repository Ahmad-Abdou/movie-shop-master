package com.ahmad.myproject.entity;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
public class MovieOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="product_id")
    private Long id;
    @OneToOne
    private Order order;
    @OneToOne
    private Movie movie;
    private int quantity;

}
