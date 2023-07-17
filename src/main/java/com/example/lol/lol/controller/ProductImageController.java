package com.example.lol.lol.controller;

import com.example.lol.lol.services.domain.ProductImageService;
import com.example.lol.lol.services.dto.ProductImageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/images")
@Slf4j
public class ProductImageController {
    private final ResourceLoader resourceLoader;

    private final ProductImageService productImageService;

    public ProductImageController(ResourceLoader resourceLoader, ProductImageService productImageService) {
        this.resourceLoader = resourceLoader;
        this.productImageService = productImageService;
    }

    @GetMapping("/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) {
        try {
            Resource resource = resourceLoader.getResource("classpath:/images/" + imageName);
            log.info("Images {}", resource.getURL());

            if (resource.exists()) {
                System.out.println(resource);
                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
            } else {
                // Handle the case when the image file does not exist
                System.out.println("not exits");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Handle the exception appropriately
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


//    @GetMapping("/api/images/product/{productId}")
//    public ResponseEntity<Resource> getAllImageProduct(@PathVariable Long productId) {
//        List<ProductImageDTO> images = productImageService.findAll();
//    }
}