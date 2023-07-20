package com.example.lol.lol.services.query;

import com.example.lol.lol.Repositories.ShipingAddressRepository;
import com.example.lol.lol.model.ShipingAddress;
import com.example.lol.lol.model.ShipingAddress_;
import com.example.lol.lol.services.criteria.ShipingAddressCriteria;
import com.example.lol.lol.services.dto.ShipingAddressDTO;
import com.example.lol.lol.services.mapper.ShipingAddressMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link ShipingAddress} entities in the database.
 * The main input is a {@link ShipingAddressCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ShipingAddressDTO} or a {@link Page} of {@link ShipingAddressDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ShipingAddressQueryService extends QueryService<ShipingAddress> {

    private final Logger log = LoggerFactory.getLogger(ShipingAddressQueryService.class);

    private final ShipingAddressRepository shipingAddressRepository;

    private final ShipingAddressMapper shipingAddressMapper;


    public ShipingAddressQueryService(
        ShipingAddressRepository shipingAddressRepository,
        ShipingAddressMapper shipingAddressMapper
    ) {
        this.shipingAddressRepository = shipingAddressRepository;
        this.shipingAddressMapper = shipingAddressMapper;

    }

    /**
     * Return a {@link List} of {@link ShipingAddressDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ShipingAddressDTO> findByCriteria(ShipingAddressCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ShipingAddress> specification = createSpecification(criteria);
        return shipingAddressMapper.toDto(shipingAddressRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ShipingAddressDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ShipingAddressDTO> findByCriteria(ShipingAddressCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ShipingAddress> specification = createSpecification(criteria);
        return shipingAddressRepository.findAll(specification, page).map(shipingAddressMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ShipingAddressCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ShipingAddress> specification = createSpecification(criteria);
        return shipingAddressRepository.count(specification);
    }

    /**
     * Function to convert {@link ShipingAddressCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ShipingAddress> createSpecification(ShipingAddressCriteria criteria) {
        Specification<ShipingAddress> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ShipingAddress_.id));
            }
            if (criteria.getOrderId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrderId(), ShipingAddress_.orderId));
            }
            if (criteria.getLocation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocation(), ShipingAddress_.location));
            }
            if (criteria.getCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCity(), ShipingAddress_.city));
            }
            if (criteria.getFullname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFullname(), ShipingAddress_.fullname));
            }
        }
        return specification;
    }
}
