package com.example.lol.lol.services.domain;

import com.example.lol.lol.services.dto.ShipingAddressDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShipingAddressService {
    /**
     * Save a shipingAddress.
     *
     * @param shipingAddressDTO the entity to save.
     * @return the persisted entity.
     */
    ShipingAddressDTO save(ShipingAddressDTO shipingAddressDTO);

    /**
     * Partially updates a shipingAddress.
     *
     * @param shipingAddressDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ShipingAddressDTO> partialUpdate(ShipingAddressDTO shipingAddressDTO);

    /**
     * Get all the shipingAddresses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ShipingAddressDTO> findAll(Pageable pageable);

    /**
     * Get the "id" shipingAddress.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ShipingAddressDTO> findOne(Long id);

    /**
     * Delete the "id" shipingAddress.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

}
