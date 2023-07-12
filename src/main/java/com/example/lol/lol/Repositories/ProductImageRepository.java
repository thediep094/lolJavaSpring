package com.example.lol.lol.Repositories;

import com.example.lol.lol.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    ProductImage findByName(String name);
    <Optional>ProductImage findProductImageById(Long id);

    @Override
    void deleteById(Long id);
}
