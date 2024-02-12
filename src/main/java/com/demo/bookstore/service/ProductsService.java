package com.demo.bookstore.service;

import com.demo.bookstore.entity.ProductDetails;
import com.demo.bookstore.exception.InvalidOptionException;
import com.demo.bookstore.exception.ResourceNotFoundException;
import com.demo.bookstore.model.DiscountTaxRequest;
import com.demo.bookstore.model.Product;
import com.demo.bookstore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductsService {

    @Autowired
    ProductRepository productRepository;

    public ProductDetails fetchProductDetails(int productId){
        Optional<ProductDetails> productDetails=productRepository.findById(productId);
        if(productDetails.isPresent())
            return productDetails.get();
        else
            throw new ResourceNotFoundException(productId);
    }

    public ProductDetails createProduct(Product product){
        ProductDetails productDetails=new ProductDetails();
        productDetails.setName(product.getName());
        productDetails.setDescription(product.getName());
        productDetails.setPrice(product.getPrice());
        productDetails.setQuantity(product.getPrice());
        return productRepository.save(productDetails);
    }

    public String updateProduct(ProductDetails productDetails){
        if(productRepository.existsById(productDetails.getProductId())){
            productRepository.save(productDetails);
            return "Product updated successfully with productId : " + productDetails.getProductId();
        }
        else
            throw new ResourceNotFoundException(productDetails.getProductId());
    }

    public String deleteProduct(int productId) {
        if(productRepository.existsById(productId)){
            productRepository.deleteById(productId);
            return "Product deleted successfully with productId : " + productId;
        }
        else
            throw new ResourceNotFoundException(productId);
    }

    public ProductDetails modifyProduct(DiscountTaxRequest discountTaxRequest){
        Optional<ProductDetails> productDetailsOptional=productRepository.findById(discountTaxRequest.getProductId());
        if(productDetailsOptional.isPresent()) {
            ProductDetails productDetails = productDetailsOptional.get();
            int modifiedPrice;
            if (discountTaxRequest.getOption()!=null && discountTaxRequest.getOption().equalsIgnoreCase("discount")) {
                modifiedPrice = productDetails.getPrice() - discountTaxRequest.getValue() * productDetails.getPrice() / 100;
                productDetails.setPrice(modifiedPrice);

            } else if (discountTaxRequest.getOption()!=null && discountTaxRequest.getOption().equalsIgnoreCase("tax")) {
                modifiedPrice = productDetails.getPrice() + discountTaxRequest.getValue() * productDetails.getPrice() / 100;
                productDetails.setPrice(modifiedPrice);
            }
            else
                throw new InvalidOptionException();
            return productRepository.save(productDetails);
        }
        else
            throw new ResourceNotFoundException(discountTaxRequest.getProductId());


    }
}
