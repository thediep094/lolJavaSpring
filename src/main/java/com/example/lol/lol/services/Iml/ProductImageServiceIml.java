package com.example.lol.lol.services.Iml;

import com.example.lol.lol.Repositories.ProductImageRepository;
import com.example.lol.lol.model.ProductImage;
import com.example.lol.lol.services.domain.ProductImageService;
import com.example.lol.lol.services.dto.ProductImageDTO;
import com.example.lol.lol.services.mapper.ProductImageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@Transactional
@Slf4j
public class ProductImageServiceIml implements ProductImageService {

    private final ProductImageRepository productImageRepository;

    private final ProductImageMapper productImageMapper;

    public ProductImageServiceIml(ProductImageRepository productImageRepository, ProductImageMapper productImageMapper) {
        this.productImageRepository = productImageRepository;
        this.productImageMapper = productImageMapper;
    }


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
    public List<ProductImageDTO> findAllByProductId(Long productImageDTOId) {
        List<ProductImage> productImages = productImageRepository.findAllByProductId(productImageDTOId);

        return productImageMapper.toDto(productImages);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductImage : {}", id);
        productImageRepository.deleteById(id);
    }

    @Override
    public void deleteAllByProductId(Long productId) {
        log.debug("Request to delete all ProductImages by productId: {}", productId);
        List<ProductImage> productImages = productImageRepository.findAllByProductId(productId);
        productImageRepository.deleteAll(productImages);
    }

    @Override
    public void saveProductImages(Long productId, MultipartFile[] files) {
        // Delete old image
        deleteAllByProductId(productId);

        String imageUploadDirectory = "src/main/resources/images"; // Change to the desired folder name within your project


        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile file : files) {
            String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            try {
                String filePath = new File(imageUploadDirectory).getAbsolutePath() + File.separator + filename;

                // Save the file to the specified directory
                file.transferTo(new File(filePath));

                imageUrls.add(filename);
                log.info("Upload to upload image: {}", filename);
            } catch (IOException e) {
                // Handle the exception appropriately
                log.error("Failed to upload image: {}", e.getMessage());
            }
        }

        // Save the image URLs to your database or perform any necessary operations
        for (String imageUrl : imageUrls) {
            ProductImageDTO productImageDTO = new ProductImageDTO();
            productImageDTO.setProductId(productId);
            productImageDTO.setUrl(imageUrl);
            save(productImageDTO);
        }
    }


}
