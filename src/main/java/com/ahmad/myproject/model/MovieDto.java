package com.ahmad.myproject.model;

import lombok.Data;

@Data
public class MovieDto {
    private String UUID;
    private String title;
    private double price;
    private String description;
    private int quantity;
}
