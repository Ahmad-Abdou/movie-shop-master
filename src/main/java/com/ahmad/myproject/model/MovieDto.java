package com.ahmad.myproject.model;

import lombok.Data;

@Data
public class MovieDto {
    private Long id;
    private String title;
    private double price;
    private String description;
    private int quantity;
    private byte[] image;

}
