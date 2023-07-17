package com.example.lol.lol.controller;

import com.example.lol.lol.services.criteria.ProductImageCriteria;
import com.example.lol.lol.services.domain.ProductImageService;
import com.example.lol.lol.services.dto.ProductImageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.lol.lol.services.query.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class ProductImageController {

    private String applicationName = "LolApp";

    private static final String ENTITY_NAME = "productImage";

    private final ResourceLoader resourceLoader;

    private final ProductImageService productImageService;

    private final ProductImageQueryService productImageQueryService;

    @GetMapping("/images/{imageName}")
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

    /**
     * {@code GET  /images} : get all the productImages.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productImages in body.
     */
    @GetMapping("/images")
    public ResponseEntity<List<ProductImageDTO>> getAllProductImages(ProductImageCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProductImages by criteria: {}", criteria);
        Page<ProductImageDTO> page = productImageQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-images/count} : count all the productImages.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/images/count")
    public ResponseEntity<Long> countProductImages(ProductImageCriteria criteria) {
        log.debug("REST request to count ProductImages by criteria: {}", criteria);
        return ResponseEntity.ok().body(productImageQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /images/product/:id} : get the "id" productImage.
     *
     * @param productId the id of the productImageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productImageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/images/product/{productId}")
    public ResponseEntity<List<ProductImageDTO>> getProductImageByProductId(@PathVariable("productId") Long productId) {
        log.debug("REST request to get All ProductImage of Product : {}", productId);
        List<ProductImageDTO> productImageDTOs = productImageService.findAllByProductId(productId);
        return ResponseEntity.ok().body(productImageDTOs);
    }

    /**
     * {@code DELETE  /product-images/:id} : delete the "id" productImage.
     *
     * @param id the id of the productImageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/images/{id}")
    public ResponseEntity<Void> deleteProductImage(@PathVariable Long id) {
        log.debug("REST request to delete ProductImage : {}", id);
        productImageService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }


}