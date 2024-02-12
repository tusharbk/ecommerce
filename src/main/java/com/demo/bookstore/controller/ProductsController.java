package com.demo.bookstore.controller;

import com.demo.bookstore.aspect.Logger;
import com.demo.bookstore.entity.ProductDetails;
import com.demo.bookstore.model.DiscountTaxRequest;
import com.demo.bookstore.model.Product;
import com.demo.bookstore.service.ProductsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
@Slf4j
public class ProductsController {

    @Autowired
    ProductsService productsService;

    @Logger
    @GetMapping(value = "/product-details",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDetails> getProductDetails(@RequestParam int productId ){
        ProductDetails productDetails=productsService.fetchProductDetails(productId);
        return new ResponseEntity<>(productDetails, HttpStatus.OK);
    }

    @Logger
    @PostMapping(value = "/create-product")
    public ResponseEntity<ProductDetails> createProduct(@RequestBody Product product){
        ProductDetails productDetails=productsService.createProduct(product);
        return new ResponseEntity<>(productDetails, HttpStatus.OK);
    }

    @Logger
    @PutMapping(value = "/update-product")
    public ResponseEntity<String> updateProduct(@RequestBody ProductDetails productDetails){
        String message=productsService.updateProduct(productDetails);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Logger
    @DeleteMapping(value = "delete-product")
    public ResponseEntity<String> deleteProduct(@RequestParam int productId){
        String message=productsService.deleteProduct(productId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Logger
    @PostMapping(value = "apply-discount-tax")
    public ResponseEntity<ProductDetails> applyDiscountOrTax(@RequestBody DiscountTaxRequest discountTaxRequest){
        ProductDetails productDetails=productsService.modifyProduct(discountTaxRequest);
        return new ResponseEntity<>(productDetails, HttpStatus.OK);
    }
}
