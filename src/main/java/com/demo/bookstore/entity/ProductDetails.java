package com.demo.bookstore.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "product_details")
public class ProductDetails {
    @Id
    @GeneratedValue(generator = "product_details_seq")
    @SequenceGenerator(name = "product_details_seq",sequenceName = "PRODUCT_DETAILS_SEQ",allocationSize = 1)
    int productId;
    String name;
    String description;
    int price;
    int quantity;
}
