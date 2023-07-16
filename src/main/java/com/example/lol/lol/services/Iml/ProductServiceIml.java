package com.example.lol.lol.services.Iml;

import com.example.lol.lol.Repositories.ProductRepository;
import com.example.lol.lol.model.*;
import com.example.lol.lol.services.dto.ProductDTO;
import com.example.lol.lol.services.mapper.ProductMapper;
import com.example.lol.lol.services.domain.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Slf4j
public class ProductServiceIml implements ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductMapper productMapper;

    @Override
    public ProductDTO save(ProductDTO productDTO) {
        log.debug("Request to save Product : {}", productDTO);
        Product product = productMapper.toEntity(productDTO);
        product = productRepository.save(product);
        ProductDTO result = productMapper.toDto(product);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductDTO> findOne(Long id) {
        log.debug("Request to get Product : {}", id);
        return productRepository.findById(id).map(productMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Products");
        return productRepository.findAll(pageable).map(productMapper::toDto);
    }


    @Override
    public Optional<ProductDTO> partialUpdate(ProductDTO productDTO) {
        log.debug("Request to partially update Product : {}", productDTO);

        return productRepository
                .findById(productDTO.getId())
                .map(
                        existingProduct -> {
                            productMapper.partialUpdate(existingProduct, productDTO);

                            return existingProduct;
                        }
                )
                .map(productRepository::save)
                .map(productMapper::toDto);
    }




    @Override
    public void delete(Long id) {
        log.debug("Request to delete Product : {}", id);
        productRepository.deleteById(id);
    }


    // check exists
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }
}
