package com.demo.bookstore.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscountTaxRequest {
    int productId;
    String option;
    @Min(value = 1 , message = "Please enter a valid number between 1 and 100")
    @Max(value = 100, message = "Please enter a valid number between 1 and 100")
    int value;
}
