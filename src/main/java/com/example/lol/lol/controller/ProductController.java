package com.example.lol.lol.controller;

import com.example.lol.lol.model.*;
import com.example.lol.lol.services.dto.ProductDTO;
import com.example.lol.lol.services.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
@Slf4j
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/")
    public ResponseEntity<ResponseObject> getAll(@RequestParam(required = false) String filter,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<ProductDTO> productPage;
        if (filter != null && !filter.isEmpty()) {
            // Apply filter logic here if needed
            productPage = productService.findAll(pageable);
        } else {
            productPage = productService.findAll(pageable);
        }

        List<ProductDTO> paginatedProducts = productPage.getContent();

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK", "Fetch success", paginatedProducts)
        );
    }

    //Get Product by Id
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getProduct(@PathVariable Long id) {
        Optional<ProductDTO> optionalProduct = productService.findOne(id);
        if (optionalProduct.isPresent()) {
            ProductDTO product = optionalProduct.get();
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

        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(productRequest.getName());
        productDTO.setDescription(productRequest.getDescription());
        productDTO.setPrice(productRequest.getPrice());
        productDTO.setCompareAtPrice(productRequest.getCompareAtPrice());
        productDTO.setEstimatedShipDate(productRequest.getEstimatedShipDate());
        // Create ProductImage and add it to the productDTO if needed

        ProductDTO createdProduct = productService.save(productDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseObject("Created", "Product created successfully", createdProduct)
        );
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<ResponseObject> putProduct(@PathVariable Long id, @RequestBody ProductRequestDto productRequest) {
        Optional<ProductDTO> optionalProduct = productService.findOne(id);
        if (optionalProduct.isPresent()) {
            ProductDTO existingProduct = optionalProduct.get();

            // Update the existing product with the new information
            existingProduct.setName(productRequest.getName());
            existingProduct.setDescription(productRequest.getDescription());
            existingProduct.setPrice(productRequest.getPrice());
            existingProduct.setCompareAtPrice(productRequest.getCompareAtPrice());
            existingProduct.setEstimatedShipDate(productRequest.getEstimatedShipDate());

            // Save the updated product
            ProductDTO updatedProduct = productService.save(existingProduct);

            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "Product updated successfully", updatedProduct)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Not found", "Product not found", null)
            );
        }
    }

}
