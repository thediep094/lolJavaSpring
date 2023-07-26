package com.example.lol.lol.controller;

import com.example.lol.lol.Repositories.ShipingAddressRepository;
import com.example.lol.lol.model.ResponseObject;
import com.example.lol.lol.services.criteria.ShipingAddressCriteria;
import com.example.lol.lol.services.domain.ShipingAddressService;
import com.example.lol.lol.services.dto.ShipingAddressDTO;
import com.example.lol.lol.services.query.ShipingAddressQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class ShipingAddressController {
    private static final String ENTITY_NAME = "Shiping Address";

    private String applicationName = "LolApp";

    private final ShipingAddressService shipingAddressService;

    private final ShipingAddressRepository shipingAddressRepository;

    private final ShipingAddressQueryService shipingAddressQueryService;
    /**
     * {@code POST  /shiping-addresses} : Create a new shipingAddress.
     *
     * @param shipingAddressDTO the shipingAddressDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shipingAddressDTO, or with status {@code 400 (Bad Request)} if the shipingAddress has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/shiping-addresses")
    public ResponseEntity<ResponseObject> createShipingAddress(@Valid @RequestBody ShipingAddressDTO shipingAddressDTO)
            throws URISyntaxException {
        log.debug("REST request to save ShipingAddress : {}", shipingAddressDTO);
        if (shipingAddressDTO.getId() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("A new order cannot already have an ID", ENTITY_NAME, "idexists")
            );
        }
        ShipingAddressDTO result = shipingAddressService.save(shipingAddressDTO);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject("OK", "Create successfully", result)
        );
    }

    /**
     * {@code PUT  /shiping-addresses/:id} : Updates an existing shipingAddress.
     *
     * @param id the id of the shipingAddressDTO to save.
     * @param shipingAddressDTO the shipingAddressDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shipingAddressDTO,
     * or with status {@code 400 (Bad Request)} if the shipingAddressDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shipingAddressDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/shiping-addresses/{id}")
    public ResponseEntity<ResponseObject> updateShipingAddress(
            @PathVariable(value = "id", required = false) final Long id,
            @Valid @RequestBody ShipingAddressDTO shipingAddressDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ShipingAddress : {}, {}", id, shipingAddressDTO);
        if (shipingAddressDTO.getId() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Invalid id", ENTITY_NAME, "idnull"));
        }
        if (!Objects.equals(id, shipingAddressDTO.getId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Invalid id", ENTITY_NAME, "idinvalid"));
        }

        if (!shipingAddressRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Entity not found", ENTITY_NAME, "idnotfound"));
        }

        ShipingAddressDTO result = shipingAddressService.save(shipingAddressDTO);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, null))
                .body(new ResponseObject("ok", "success", result));
    }

    /**
     * {@code PATCH  /shiping-addresses/:id} : Partial updates given fields of an existing shipingAddress, field will ignore if it is null
     *
     * @param id the id of the shipingAddressDTO to save.
     * @param shipingAddressDTO the shipingAddressDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shipingAddressDTO,
     * or with status {@code 400 (Bad Request)} if the shipingAddressDTO is not valid,
     * or with status {@code 404 (Not Found)} if the shipingAddressDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the shipingAddressDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/shiping-addresses/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ResponseObject> partialUpdateShipingAddress(
            @PathVariable(value = "id", required = false) final Long id,
            @NotNull @RequestBody ShipingAddressDTO shipingAddressDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ShipingAddress partially : {}, {}", id, shipingAddressDTO);
        if (shipingAddressDTO.getId() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Invalid id", ENTITY_NAME, "idnull"));
        }
        if (!Objects.equals(id, shipingAddressDTO.getId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Invalid id", ENTITY_NAME, "idinvalid"));
        }

        if (!shipingAddressRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Entity not found", ENTITY_NAME, "idnotfound"));
        }

        Optional<ShipingAddressDTO> result = shipingAddressService.partialUpdate(shipingAddressDTO);

        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, null))
                .body(new ResponseObject("ok", "success", result));
    }

    /**
     * {@code GET  /shiping-addresses} : get all the shipingAddresses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shipingAddresses in body.
     */
    @GetMapping("/shiping-addresses")
    public ResponseEntity<List<ShipingAddressDTO>> getAllShipingAddresses(ShipingAddressCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ShipingAddresses by criteria: {}", criteria);
        Page<ShipingAddressDTO> page = shipingAddressQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /shiping-addresses/count} : count all the shipingAddresses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/shiping-addresses/count")
    public ResponseEntity<Long> countShipingAddresses(ShipingAddressCriteria criteria) {
        log.debug("REST request to count ShipingAddresses by criteria: {}", criteria);
        return ResponseEntity.ok().body(shipingAddressQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /shiping-addresses/:id} : get the "id" shipingAddress.
     *
     * @param id the id of the shipingAddressDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shipingAddressDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/shiping-addresses/{id}")
    public ResponseEntity<ShipingAddressDTO> getShipingAddress(@PathVariable Long id) {
        log.debug("REST request to get ShipingAddress : {}", id);
        Optional<ShipingAddressDTO> shipingAddressDTO = shipingAddressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shipingAddressDTO);
    }

    /**
     * {@code DELETE  /shiping-addresses/:id} : delete the "id" shipingAddress.
     *
     * @param id the id of the shipingAddressDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/shiping-addresses/{id}")
    public ResponseEntity<Void> deleteShipingAddress(@PathVariable Long id) {
        log.debug("REST request to delete ShipingAddress : {}", id);
        shipingAddressService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }
}
