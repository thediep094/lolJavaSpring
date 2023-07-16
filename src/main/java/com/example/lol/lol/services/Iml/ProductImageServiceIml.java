package com.example.lol.lol.services.Iml;

import com.example.lol.lol.Repositories.ProductImageRepository;
import com.example.lol.lol.model.ProductImage;
import com.example.lol.lol.services.domain.ProductImageService;
import com.example.lol.lol.services.dto.ProductImageDTO;
import com.example.lol.lol.services.mapper.ProductImageMapper;
import com.example.lol.lol.services.specification.ProductImageSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
@Slf4j
public class ProductImageServiceIml implements ProductImageService {

    @Autowired
    ProductImageRepository productImageRepository;

    @Autowired
    ProductImageMapper productImageMapper;


    @Override
    public ProductImageDTO save(ProductImageDTO productImageDTO) {
        log.debug("Request to save ProductImage : {}", productImageDTO);
        ProductImage productImage = productImageMapper.toEntity(productImageDTO);
        productImage = productImageRepository.save(productImage);
        ProductImageDTO result = productImageMapper.toDto(productImage);
        return result;
    }

    @Override
    public Optional<ProductImageDTO> partialUpdate(ProductImageDTO productImageDTO) {
        log.debug("Request to partially update ProductImage : {}", productImageDTO);

        return productImageRepository
                .findById(productImageDTO.getId())
                .map(
                        existingProductImage -> {
                            productImageMapper.partialUpdate(existingProductImage, productImageDTO);

                            return existingProductImage;
                        }
                )
                .map(productImageRepository::save)
                .map(productImageMapper::toDto);
    }
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public Page<ProductImageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductImages");
        return productImageRepository.findAll(pageable).map(productImageMapper::toDto);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public Optional<ProductImageDTO> findOne(Long id) {
        log.debug("Request to get ProductImage : {}", id);
        return productImageRepository.findById(id).map(productImageMapper::toDto);
    }

    @Override
    public List<ProductImageDTO> findAllByProductId(Long productId) {
        Specification conditions = Specification.where(ProductImageSpecification.hasProductId(productId));
        return  productImageRepository.findAll(conditions);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductImage : {}", id);
        productImageRepository.deleteById(id);
    }



}
