package com.example.lol.lol.services.domain;

import com.example.lol.lol.services.dto.ProductImageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProductImageService {
    //Create
    ProductImageDTO save(ProductImageDTO productImageDTO);

    //Update
    Optional<ProductImageDTO> partialUpdate(ProductImageDTO productImageDTO);

    //Find
    Page<ProductImageDTO> findAll(Pageable pageable);
    Optional<ProductImageDTO> findOne(Long id);
    List<ProductImageDTO> findAllByProductId(Long productId);

    //Delete
    void delete(Long id);
    void deleteAllByProductId(Long productId);

    //save
    void saveProductImages(Long productId, MultipartFile[] files);
}

