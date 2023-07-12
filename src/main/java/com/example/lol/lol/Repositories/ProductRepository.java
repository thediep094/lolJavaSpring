package com.example.lol.lol.Repositories;

import com.example.lol.lol.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);
    <Optional>Product findProductById(Long id);
    @Override
    void deleteById(Long id);
    boolean existsByName(String name);
}
