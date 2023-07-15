package com.example.lol.lol.services.product;

import com.example.lol.lol.Repositories.ProductRepository;
import com.example.lol.lol.model.*;
import com.example.lol.lol.services.specification.ProductSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Slf4j
public class ProductServiceIml implements ProductService{
    @Autowired
    ProductRepository productRepository;


    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getProduct(Long productId) {
        log.info("Fetching product {}", productId);
        Specification<Product> spec = ProductSpecification.hasId(productId);
        Optional<Product> optionalProduct = productRepository.findOne(spec);
        return optionalProduct.orElse(null);
    }

    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product updateProduct(Product product) {
        log.info("Update product {}", product.getName());
        return productRepository.save(product);
    }



    @Override
    public void deleteProduct(Long productId) {
        log.info("Deleting product with ID: {}", productId);
        productRepository.deleteById(productId);
    }


    // check exists
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }
}
