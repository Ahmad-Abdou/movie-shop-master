package com.ahmad.myproject.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private double price;
    private String description;
    private int quantity;
    @Lob
    private byte[] image;


}
