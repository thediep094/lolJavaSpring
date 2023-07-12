package com.example.lol.lol.Repositories;

import com.example.lol.lol.model.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTagRepository extends JpaRepository<ProductTag, Long> {
    ProductTag findByName(String name);
    ProductTag findProductTagById(Long id);

    @Override
    void deleteById(Long id);
}