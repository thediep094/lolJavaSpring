package com.example.lol.lol.controller;

import com.example.lol.lol.model.*;
import com.example.lol.lol.services.criteria.ProductCriteria;
import com.example.lol.lol.services.dto.ProductDTO;
import com.example.lol.lol.services.domain.ProductService;
import com.example.lol.lol.services.dto.ProductRequestDto;

import com.example.lol.lol.services.query.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class ProductController {

    private static final String ENTITY_NAME = "product";

    private String applicationName = "LolApp";

    private final ProductService productService;

    private final ProductQueryService productQueryService;


    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts(ProductCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Products by criteria: {}", criteria);
        Page<ProductDTO> page = productQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    //Get Product
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        log.debug("REST request to get Product : {}", id);
        Optional<ProductDTO> productDTO = productService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productDTO);
    }

    //Create product (Admin)
    @PostMapping(value = "/admin/products/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseObject> createProduct(@ModelAttribute ProductRequestDto productRequest, @RequestParam("file") MultipartFile[] files) {
        ProductRequestDto createdProduct = productService.save(productRequest, files);
        if (createdProduct != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ResponseObject("Created", "Product created successfully", createdProduct)
            );
        }
        else {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(
                    new ResponseObject("Failure", "Product created failure", createdProduct)
            );
        }
    }

    /**
     * {@code PATCH  /products/:id} : Partial updates given fields of an existing product, field will ignore if it is null
     *
     * @param id the id of the productDTO to save.
     * @param productDTO the productDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productDTO,
     * or with status {@code 400 (Bad Request)} if the productDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping(value = "/admin/products/update/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProductDTO> partialUpdateProduct(
            @PathVariable(value = "id", required = false) final Long id,
            @NotNull @RequestBody ProductDTO productDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Product partially : {}, {}", id, productDTO);
        Optional<ProductDTO> result = productService.partialUpdate(productDTO);

        return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productDTO.getId().toString())
        );
    }


    @GetMapping("/products/count")
    public ResponseEntity<Long> countProducts(ProductCriteria criteria) {
        log.debug("REST request to count Products by criteria: {}", criteria);
        return ResponseEntity.ok().body(productQueryService.countByCriteria(criteria));
    }

    @DeleteMapping("/products/admin/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.debug("REST request to delete Product : {}", id);
        productService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }

}
