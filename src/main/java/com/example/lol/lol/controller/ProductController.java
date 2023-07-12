package com.example.lol.lol.controller;

import com.example.lol.lol.model.*;
import com.example.lol.lol.services.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
@Slf4j
public class ProductController {
    @Autowired
    ProductService productService;

    // Get All Products with filter
    @GetMapping("/")
    public ResponseEntity<ResponseObject> getAll(@RequestParam(required = false) String filter, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize) {
        List<Product> products;
        if ( filter != null && !filter.isEmpty() ) {
            //Not filter by anything will do later
            products = productService.getAllProducts();
        } else {
            products = productService.getAllProducts();
        }

        // Apply pagination
        int totalItems = products.size();
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);

        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalItems);

        List<Product> paginatedProducts = products.subList(startIndex, endIndex);
    
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK", "Fetch success", paginatedProducts)
        );
    }


    //Get Product by Id
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getProduct (@PathVariable Long id) {
        Product product = productService.getProduct(id);
        if (product != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "Fetch success", product)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Not found", "Fetch failure", null)
            );
        }
    }

    //Create product (Admin)
    @PostMapping("/admin/create")
    public ResponseEntity<ResponseObject> createProduct(@RequestBody ProductRequestDto productRequest) {
        // Check if a product with the same name already exists
        if (productService.existsByName(productRequest.getName())) {
            String errorMessage = "Product with the name " + productRequest.getName() + " already exists";
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ResponseObject("Error", errorMessage, null)
            );
        }
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setCompareAtPrice(productRequest.getCompareAtPrice());
        product.setEstimatedShipDate(productRequest.getEstimatedShipDate());
        // Create ProductImage and add to the product
        List<ProductImage> images = new ArrayList<>();
        for (ProductRequestDto.ProductImageDto imageDto : productRequest.getImages()) {
            ProductImage image = new ProductImage();
            image.setName(imageDto.getName());
            images.add(image);
        }
        product.setImages(images);

        // Create ProductThumbnail and add to the product
        List<ProductThumbnail> thumbnails = new ArrayList<>();
        for (ProductRequestDto.ProductThumbnailDto thumbnailDto : productRequest.getThumbnails()) {
            ProductThumbnail thumbnail = new ProductThumbnail();
            thumbnail.setName(thumbnailDto.getName());
            thumbnails.add(thumbnail);
        }
        product.setThumbnails(thumbnails);

        // Create ProductTag and add to the product
        List<ProductTag> tags = new ArrayList<>();
        for (ProductRequestDto.ProductTagDto tagDto : productRequest.getTags()) {
            ProductTag tag = new ProductTag();
            tag.setName(tagDto.getName());
            tags.add(tag);
        }
        product.setTags(tags);

        Product createdProduct = productService.saveProduct(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseObject("Created", "Product created successfully", createdProduct)
        );
    }


    // Change product information (Admin)
    @PutMapping("/admin/{id}")
    public ResponseEntity<ResponseObject> putProduct (@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("Not found", "Fetch failure", null)
        );
    }
}
