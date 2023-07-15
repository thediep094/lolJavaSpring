package com.example.lol.lol.Repositories;

import com.example.lol.lol.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long>, JpaSpecificationExecutor<ProductImage> {

}
