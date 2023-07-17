package com.example.lol.lol.services.domain;

import com.example.lol.lol.model.*;
import com.example.lol.lol.services.dto.ProductDTO;
import com.example.lol.lol.services.dto.ProductRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    // Create new
    ProductRequestDto save(ProductRequestDto productDTO, MultipartFile[] files);

    //Get
    Optional<ProductDTO> findOne(Long id);
    Page<ProductDTO> findAll(Pageable pageable);


    // Update
    Optional<ProductDTO> partialUpdate(ProductDTO productDTO);

    // Delete
    void delete(Long id);

    // Search
//    Page<ProductDTO> search(String query, Pageable pageable);

    //check exists
    boolean existsByName(String name);

    //Validate
    Boolean checkExistsByName(ProductRequestDto productRequest);

}
