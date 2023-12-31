package com.example.lol.lol.services.Iml;

import com.example.lol.lol.Repositories.ProductRepository;
import com.example.lol.lol.model.*;
import com.example.lol.lol.services.domain.ProductImageService;
import com.example.lol.lol.services.dto.ProductDTO;
import com.example.lol.lol.services.dto.ProductImageDTO;
import com.example.lol.lol.services.dto.ProductRequestDto;
import com.example.lol.lol.services.mapper.ProductDTOMapper;
import com.example.lol.lol.services.mapper.ProductMapper;
import com.example.lol.lol.services.domain.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@RequestScope
public class ProductServiceIml implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final ProductImageService productImageService;

    private final ProductDTOMapper productDTOMapper;

    private ProductRequestDto form;

    public ProductServiceIml(ProductRepository productRepository, ProductMapper productMapper, ProductImageService productImageService, ProductDTOMapper productDTOMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.productImageService = productImageService;
        this.productDTOMapper = productDTOMapper;
    }

    // Validate
    public Boolean checkExistsByName(ProductRequestDto productRequest) {
        // Check if a product with the same name already exists
        if (productRepository.existsByName(productRequest.getName())) {
            log.error("Product with the name {} already exists", productRequest.getName());
            return true;
        }
        else {
           return false;
        }
    }



    @Override
    public ProductRequestDto saveNew(ProductRequestDto productRequest, MultipartFile[] files) {
        this.form = productRequest;
        if(checkExistsByName(form)) {
            return null;
        }
        insertProduct(files);
        return productRequest;
    }

    private void insertProduct(MultipartFile[] files) {
        ProductDTO productDTO = productDTOMapper.toEntity(form);
        log.debug("Request to save Product : {}", productDTO);
        Product product = productMapper.toEntity(productDTO);
        Product newProduct = productRepository.save(product);
        insertProductImage(newProduct.getId(),files);
    }

    private void insertProductImage(Long id, MultipartFile[] files) {
        productImageService.saveProductImages(id, files);
    }

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
        productImageService.deleteAllByProductId(id);
    }


    // check exists
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }
}
