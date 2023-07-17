package com.example.lol.lol.services.query;

import com.example.lol.lol.Repositories.ProductImageRepository;
import com.example.lol.lol.model.ProductImage;
import com.example.lol.lol.model.ProductImage_;
import com.example.lol.lol.services.criteria.ProductImageCriteria;
import com.example.lol.lol.services.dto.ProductImageDTO;
import com.example.lol.lol.services.mapper.ProductImageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

import java.util.List;

@Service
@Transactional(readOnly = true)

public class ProductImageQueryService  extends QueryService<ProductImage> {
    private final Logger log = LoggerFactory.getLogger(ProductImageQueryService.class);

    @Autowired
    ProductImageRepository productImageRepository;

    @Autowired
    ProductImageMapper productImageMapper;

    public ProductImageQueryService(
            ProductImageRepository productImageRepository,
            ProductImageMapper productImageMapper
    ) {
        this.productImageRepository = productImageRepository;
        this.productImageMapper = productImageMapper;
    }

    @Transactional(readOnly = true)
    public List<ProductImageDTO> findByCriteria(ProductImageCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductImage> specification = createSpecification(criteria);
        return productImageMapper.toDto(productImageRepository.findAll(specification));
    }

    @Transactional(readOnly = true)
    public Page<ProductImageDTO> findByCriteria(ProductImageCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductImage> specification = createSpecification(criteria);
        return productImageRepository.findAll(specification, page).map(productImageMapper::toDto);
    }

    @Transactional(readOnly = true)
    public long countByCriteria(ProductImageCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductImage> specification = createSpecification(criteria);
        return productImageRepository.count(specification);
    }

    protected Specification<ProductImage> createSpecification(ProductImageCriteria criteria) {
        Specification<ProductImage> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductImage_.id));
            }
            if (criteria.getUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrl(), ProductImage_.url));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProductId(), ProductImage_.productId));
            }
        }
        return specification;
    }
}
