package com.demo.bookstore.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    String name;
    String description;
    int price;
    int quantity;
}
