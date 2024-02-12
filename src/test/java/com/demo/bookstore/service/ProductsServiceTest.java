package com.demo.bookstore.service;

import com.demo.bookstore.entity.ProductDetails;
import com.demo.bookstore.exception.InvalidOptionException;
import com.demo.bookstore.exception.ResourceNotFoundException;
import com.demo.bookstore.model.DiscountTaxRequest;
import com.demo.bookstore.model.Product;
import com.demo.bookstore.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductsServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductsService productsService;

    @Test
    void testFetchProductDetails(){
        when(productRepository.findById(1)).thenReturn(Optional.ofNullable(mockProductDetails()));
        ProductDetails productDetails=productsService.fetchProductDetails(1);
        Assertions.assertEquals(productDetails.getProductId(),1);
        Assertions.assertEquals(productDetails.getName(),"Test");
    }

    @Test
    void testFetchProductDetails_exception(){
        when(productRepository.findById(2)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class,()->productsService.fetchProductDetails(2));
    }

    @Test
    void testCreateProduct(){
        when(productRepository.save(any())).thenReturn(mockProductDetails());
        Product product=new Product();
        product.setName("Test");
        product.setDescription("Test product");
        product.setPrice(30);
        product.setQuantity(50);
        ProductDetails productDetails=productsService.createProduct(product);
        Assertions.assertEquals(productDetails.getProductId(),1);
        Assertions.assertEquals(productDetails.getName(),"Test");
    }

    @Test
    void testUpdateProduct(){
        when(productRepository.existsById(anyInt())).thenReturn(true);
        when(productRepository.save(any())).thenReturn(mockProductDetails());
        String message=productsService.updateProduct(mockProductDetails());
        Assertions.assertEquals("Product updated successfully with productId : 1",message);
    }

    @Test
    void testUpdateProduct_exception(){
        when(productRepository.existsById(anyInt())).thenReturn(false);
        Assertions.assertThrows(ResourceNotFoundException.class,()->productsService.updateProduct(mockProductDetails()));
    }

    @Test
    void testDeleteProduct(){
        when(productRepository.existsById(anyInt())).thenReturn(true);
        doNothing().when(productRepository).deleteById(anyInt());
        String message=productsService.deleteProduct(1);
        Assertions.assertEquals("Product deleted successfully with productId : 1",message);
    }
    @Test
    void testDeleteProduct_exception(){
        when(productRepository.existsById(anyInt())).thenReturn(false);
        Assertions.assertThrows(ResourceNotFoundException.class,()->productsService.deleteProduct(2));
    }

    @Test
    void testModifyProduct_discount(){
        when(productRepository.findById(anyInt())).thenReturn(Optional.ofNullable(mockProductDetails()));
        DiscountTaxRequest discountTaxRequest=new DiscountTaxRequest();
        discountTaxRequest.setProductId(1);
        discountTaxRequest.setValue(50);
        discountTaxRequest.setOption("discount");
        ProductDetails updatedProduct=mockProductDetails();
        updatedProduct.setPrice(15);
        when(productRepository.save(any())).thenReturn(updatedProduct);
        productsService.modifyProduct(discountTaxRequest);
    }

    @Test
    void testModifyProduct_tax(){
        when(productRepository.findById(anyInt())).thenReturn(Optional.ofNullable(mockProductDetails()));
        DiscountTaxRequest discountTaxRequest=new DiscountTaxRequest();
        discountTaxRequest.setProductId(1);
        discountTaxRequest.setValue(50);
        discountTaxRequest.setOption("tax");
        ProductDetails updatedProduct=mockProductDetails();
        updatedProduct.setPrice(45);
        when(productRepository.save(any())).thenReturn(updatedProduct);
        productsService.modifyProduct(discountTaxRequest);
    }

    @Test
    void testModifyProduct_resourceNotFound(){
        when(productRepository.findById(anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class,()->productsService.modifyProduct(new DiscountTaxRequest()));
    }

    @Test
    void testModifyProduct_invalidOption(){
        when(productRepository.findById(anyInt())).thenReturn(Optional.ofNullable(mockProductDetails()));
        DiscountTaxRequest discountTaxRequest=new DiscountTaxRequest();
        discountTaxRequest.setProductId(1);
        discountTaxRequest.setValue(50);
        discountTaxRequest.setOption("test");
        Assertions.assertThrows(InvalidOptionException.class,()->productsService.modifyProduct(discountTaxRequest));
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
