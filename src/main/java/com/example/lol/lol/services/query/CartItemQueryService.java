package com.example.lol.lol.services.query;

import com.example.lol.lol.Repositories.CartItemRepository;
import com.example.lol.lol.model.CartItem;
import com.example.lol.lol.model.CartItem_;
import com.example.lol.lol.services.criteria.CartItemCriteria;
import com.example.lol.lol.services.dto.CartItemDTO;
import com.example.lol.lol.services.mapper.CartItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CartItemQueryService extends QueryService<CartItem> {

    private final Logger log = LoggerFactory.getLogger(CartItemQueryService.class);

    private final CartItemRepository cartItemRepository;

    private final CartItemMapper cartItemMapper;


    public CartItemQueryService(
        CartItemRepository cartItemRepository,
        CartItemMapper cartItemMapper
    ) {
        this.cartItemRepository = cartItemRepository;
        this.cartItemMapper = cartItemMapper;
    }

    /**
     * Return a {@link List} of {@link com.example.lol.lol.services.dto.CartItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CartItemDTO> findByCriteria(CartItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CartItem> specification = createSpecification(criteria);
        return cartItemMapper.toDto(cartItemRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CartItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CartItemDTO> findByCriteria(CartItemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CartItem> specification = createSpecification(criteria);
        return cartItemRepository.findAll(specification, page).map(cartItemMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CartItemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CartItem> specification = createSpecification(criteria);
        return cartItemRepository.count(specification);
    }

    /**
     * Function to convert {@link CartItemCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CartItem> createSpecification(CartItemCriteria criteria) {
        Specification<CartItem> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CartItem_.id));
            }
            if (criteria.getCartId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCartId(), CartItem_.cartId));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProductId(), CartItem_.productId));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), CartItem_.quantity));
            }
        }
        return specification;
    }
}
