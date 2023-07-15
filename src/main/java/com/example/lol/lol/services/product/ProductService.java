package com.example.lol.lol.services.product;

import com.example.lol.lol.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    // Create new
    Product saveProduct(Product product);

    //Get
    Product getProduct(Long productId);
    Page<Product> getAllProducts(Pageable pageable);


    // Update
    Product updateProduct(Product product);

    // Delete
    void deleteProduct(Long productId);

    //check exists
    boolean existsByName(String name);

}
