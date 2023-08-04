package com.example.lol.lol.services.query;

import com.example.lol.lol.Repositories.ProductImageRepository;
import com.example.lol.lol.Repositories.ProductRepository;
import com.example.lol.lol.model.Product;
import com.example.lol.lol.services.criteria.ProductCriteria;
import com.example.lol.lol.services.domain.ProductImageService;
import com.example.lol.lol.services.dto.ProductDTO;
import com.example.lol.lol.services.mapper.ProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;
import com.example.lol.lol.model.Product_;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductQueryService extends QueryService<Product> {
    private final Logger log = LoggerFactory.getLogger(ProductQueryService.class);

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductImageRepository productImageRepository;

    public ProductQueryService(
            ProductRepository productRepository,
            ProductMapper productMapper,
            ProductImageRepository productImageRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.productImageRepository = productImageRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> findByCriteria(ProductCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Product> specification = createSpecification(criteria);
        return  productMapper.toDto(productRepository.findAll(specification));
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findByCriteria(ProductCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Product> specification = createSpecification(criteria);
        Page<ProductDTO> productDTOS = productRepository.findAll(specification, page).map(productMapper::toDto);
        for (ProductDTO productDTO : productDTOS) {
            String image = productImageRepository.findFirstByProductId(productDTO.getId()).getUrl();
            log.debug("Image {}", image);
            productDTO.setImage(image);
        }
        return productDTOS;
    }

    @Transactional(readOnly = true)
    public long countByCriteria(ProductCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Product> specification = createSpecification(criteria);
        return productRepository.count(specification);
    }

    protected Specification<Product> createSpecification(ProductCriteria criteria) {
        Specification<Product> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Product_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Product_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Product_.description));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), Product_.price));
            }
            if (criteria.getCompareAtPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompareAtPrice(), Product_.compareAtPrice));
            }
        }
        return specification;
    }


}
