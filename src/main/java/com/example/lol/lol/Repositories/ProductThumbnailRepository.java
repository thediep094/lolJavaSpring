package com.example.lol.lol.Repositories;

import com.example.lol.lol.model.ProductThumbnail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductThumbnailRepository extends JpaRepository<ProductThumbnail, Long> {
    ProductThumbnail findByName(String name);

    ProductThumbnail findProductThumbnailById(Long id);

    @Override
    void deleteById(Long id);
}
