package com.example.lol.lol.controller;

import com.example.lol.lol.Repositories.CartRepository;
import com.example.lol.lol.model.ResponseObject;
import com.example.lol.lol.services.criteria.CartCriteria;
import com.example.lol.lol.services.domain.CartService;
import com.example.lol.lol.services.dto.CartDTO;
import com.example.lol.lol.services.query.CartQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class CartController {
    private static final String ENTITY_NAME = "cart";

    private String applicationName = "LolApp";

    private final CartService cartService;

    private final CartRepository cartRepository;

    private final CartQueryService cartQueryService;


    /**
     * {@code POST  /carts} : Create a new cart.
     *
     * @param cartDTO the cartDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cartDTO, or with status {@code 400 (Bad Request)} if the cart has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */

    @PreAuthorize("hasRole('ADMIN') or #cartDTO.username == authentication.name")
    @PostMapping("/carts")
    public ResponseEntity<ResponseObject> createCart(@Valid @RequestBody CartDTO cartDTO) throws URISyntaxException {

        log.debug("REST request to save Cart : {}", cartDTO);
        if (cartDTO.getUsername() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("A new cart need unique username", ENTITY_NAME, "")
            );
        }
        CartDTO result = cartService.save(cartDTO);
        if (result != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Created successfully", ENTITY_NAME, result)
            );
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("Username already have a cart", ENTITY_NAME, null));
        }

    }

    /**
     * {@code PUT  /carts/:id} : Updates an existing cart.
     *
     * @param id the id of the cartDTO to save.
     * @param cartDTO the cartDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cartDTO,
     * or with status {@code 400 (Bad Request)} if the cartDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cartDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PreAuthorize("hasRole('ADMIN') or #cartDTO.username == authentication.name")
    @PutMapping("/admin/carts/{id}")
    public ResponseEntity<ResponseObject> updateCart(
            @PathVariable(value = "id", required = false) final Long id,
            @Valid @RequestBody CartDTO cartDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Cart : {}, {}", id, cartDTO);
        if (cartDTO.getId() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Invalid id", ENTITY_NAME, "idnull"));

        }
        if (!Objects.equals(id, cartDTO.getId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Invalid id", ENTITY_NAME, "idinvalid"));

        }

        if (!cartRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Entity not found", ENTITY_NAME, "idnotfound"));

        }

        CartDTO result = cartService.save(cartDTO);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, null))
                .body(new ResponseObject("ok", "success", result));
    }

    /**
     * {@code PATCH  /carts/:id} : Partial updates given fields of an existing cart, field will ignore if it is null
     *
     * @param id the id of the cartDTO to save.
     * @param cartDTO the cartDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cartDTO,
     * or with status {@code 400 (Bad Request)} if the cartDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cartDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cartDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PreAuthorize("hasRole('ADMIN') or #cartDTO.username == authentication.name")
    @PatchMapping(value = "/admin/carts/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ResponseObject> partialUpdateCart(
            @PathVariable(value = "id", required = false) final Long id,
            @NotNull @RequestBody CartDTO cartDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Cart partially : {}, {}", id, cartDTO);
        if (cartDTO.getId() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Invalid id", ENTITY_NAME, "idnull"));

        }
        if (!Objects.equals(id, cartDTO.getId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Invalid id", ENTITY_NAME, "idinvalid"));
        }

        if (!cartRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Entity not found", ENTITY_NAME, "idnotfound"));
        }

        Optional<CartDTO> result = cartService.partialUpdate(cartDTO);

        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, null))
                .body(new ResponseObject("ok", "success", result));
    }

    /**
     * {@code GET  /carts} : get all the carts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of carts in body.
     */
    @GetMapping("/admin/carts")
    public ResponseEntity<List<CartDTO>> getAllCarts(CartCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Carts by criteria: {}", criteria);
        Page<CartDTO> page = cartQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /carts/count} : count all the carts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/admin/carts/count")
    public ResponseEntity<Long> countCarts(CartCriteria criteria) {
        log.debug("REST request to count Carts by criteria: {}", criteria);
        return ResponseEntity.ok().body(cartQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /carts/:username} : get the "username" cart.
     *
     * @param username the id of the cartDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cartDTO, or with status {@code 404 (Not Found)}.
     */
    @PreAuthorize("hasRole('ADMIN') or #username == authentication.name")
    @GetMapping("/carts/{username}")
    public ResponseEntity<CartDTO> getCart(@PathVariable String username) {
        log.debug("REST request to get Cart : {}", username);
        Optional<CartDTO> cartDTO = cartService.findOne(username);
        return ResponseUtil.wrapOrNotFound(cartDTO);
    }

    /**
     * {@code DELETE  /carts/:id} : delete the "id" cart.
     *
     * @param id the id of the cartDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/admin/carts/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        log.debug("REST request to delete Cart : {}", id);
        cartService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }

}
