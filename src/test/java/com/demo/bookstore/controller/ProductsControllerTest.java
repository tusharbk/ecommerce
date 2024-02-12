package com.demo.bookstore.controller;

import com.demo.bookstore.entity.ProductDetails;
import com.demo.bookstore.model.DiscountTaxRequest;
import com.demo.bookstore.model.Product;
import com.demo.bookstore.service.ProductsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductsControllerTest {

    @Mock
    ProductsService productsService;
    @InjectMocks
    ProductsController productsController;

    @Test
    void testGetProductDetails() {
        when(productsService.fetchProductDetails(anyInt())).thenReturn(mockProductDetails());
        ResponseEntity<ProductDetails> responseEntity = productsController.getProductDetails(1);
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatusCode.valueOf(200));
        Assertions.assertEquals(responseEntity.getBody().getProductId(),1);
    }

    @Test
    void testCreateProduct(){
        when(productsService.createProduct(any())).thenReturn(mockProductDetails());
        Product product=new Product();
        product.setName("Test");
        product.setDescription("Test product");
        product.setPrice(30);
        product.setQuantity(50);
        ResponseEntity<ProductDetails> responseEntity = productsController.createProduct(product);
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatusCode.valueOf(200));
        Assertions.assertEquals(responseEntity.getBody().getProductId(),1);
    }

    @Test
    void testUpdateProduct(){
        when(productsService.updateProduct(any())).thenReturn("Product updated successfully with productId : 1");
        ResponseEntity<String> responseEntity = productsController.updateProduct(mockProductDetails());
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatusCode.valueOf(200));
        Assertions.assertEquals(responseEntity.getBody(),"Product updated successfully with productId : 1");
    }

    @Test
    void testDeleteProduct(){
        when(productsService.deleteProduct(anyInt())).thenReturn("Product deleted successfully with productId : 1");
        ResponseEntity<String> responseEntity = productsController.deleteProduct(1);
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatusCode.valueOf(200));
        Assertions.assertEquals(responseEntity.getBody(),"Product deleted successfully with productId : 1");
    }

    @Test
    void testApplyDiscountOrTaxTest(){
        when(productsService.modifyProduct(any())).thenReturn(mockProductDetails());
        DiscountTaxRequest discountTaxRequest=new DiscountTaxRequest();
        discountTaxRequest.setProductId(1);
        discountTaxRequest.setOption("discount");
        discountTaxRequest.setValue(50);
        ResponseEntity<ProductDetails> responseEntity=productsController.applyDiscountOrTax(discountTaxRequest);
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatusCode.valueOf(200));
        Assertions.assertEquals(responseEntity.getBody().getProductId(),1);
    }

    public ProductDetails mockProductDetails(){
        ProductDetails productDetails=new ProductDetails();
        productDetails.setProductId(1);
        productDetails.setName("Test");
        productDetails.setDescription("Test product");
        productDetails.setPrice(30);
        productDetails.setQuantity(50);
        return productDetails;
    }
}
