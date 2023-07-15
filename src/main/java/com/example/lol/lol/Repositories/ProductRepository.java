package com.example.lol.lol.Repositories;

import com.example.lol.lol.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Product findByName(String name);
    <Optional>Product findProductById(Long id);
    @Query("SELECT p, pi FROM Product p JOIN ProductImage pi ON p.id = pi.productId WHERE p.id = :productId")
    Optional<Object[]> findProductWithImageById(@Param("productId") Long productId);

    @Override
    void deleteById(Long id);
    boolean existsByName(String name);
}
