package com.example.lol.lol.controller;

import com.example.lol.lol.Repositories.CartItemRepository;
import com.example.lol.lol.model.ResponseObject;
import com.example.lol.lol.services.criteria.CartItemCriteria;
import com.example.lol.lol.services.domain.CartItemService;
import com.example.lol.lol.services.dto.CartItemDTO;
import com.example.lol.lol.services.query.CartItemQueryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.protocol.HTTP;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@AllArgsConstructor
@Slf4j
public class CartItemController {
    private static final String ENTITY_NAME = "cartItem";

    private final CartItemService cartItemService;

    private final CartItemRepository cartItemRepository;

    private final CartItemQueryService cartItemQueryService;

    /**
     * {@code POST  /cart-items} : Create a new cartItem.
     *
     * @param cartItemDTO the cartItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cartItemDTO, or with status {@code 400 (Bad Request)} if the cartItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cart-items")
    public ResponseEntity<ResponseObject> createCartItem(@Valid @RequestBody CartItemDTO cartItemDTO) throws URISyntaxException {
        log.debug("REST request to save CartItem : {}", cartItemDTO);
        if (cartItemDTO.getId() != null) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("A new cartItem cannot already have an ID", ENTITY_NAME, "idexists"));
        }
        CartItemDTO result = cartItemService.save(cartItemDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Create cart item successfully", result));
    }

    /**
     * {@code PUT  /cart-items/:id} : Updates an existing cartItem.
     *
     * @param id the id of the cartItemDTO to save.
     * @param cartItemDTO the cartItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cartItemDTO,
     * or with status {@code 400 (Bad Request)} if the cartItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cartItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cart-items/{id}")
    public ResponseEntity<ResponseObject> updateCartItem(
            @PathVariable(value = "id", required = false) final Long id,
            @Valid @RequestBody CartItemDTO cartItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CartItem : {}, {}", id, cartItemDTO);
        if (cartItemDTO.getId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("Invalid id", ENTITY_NAME, "idnull"));
        }
        if (!Objects.equals(id, cartItemDTO.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("Invalid ID", ENTITY_NAME, "idinvalid"));
        }

        if (!cartItemRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("Entity not found", ENTITY_NAME, "idnotfound"));
        }

        CartItemDTO result = cartItemService.save(cartItemDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Update successfully", result));
    }

    /**
     * {@code PATCH  /cart-items/:id} : Partial updates given fields of an existing cartItem, field will ignore if it is null
     *
     * @param id the id of the cartItemDTO to save.
     * @param cartItemDTO the cartItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cartItemDTO,
     * or with status {@code 400 (Bad Request)} if the cartItemDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cartItemDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cartItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cart-items/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ResponseObject> partialUpdateCartItem(
            @PathVariable(value = "id", required = false) final Long id,
            @NotNull @RequestBody CartItemDTO cartItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CartItem partially : {}, {}", id, cartItemDTO);
        if (cartItemDTO.getId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("Invalid id", ENTITY_NAME, "idnull"));
        }
        if (!Objects.equals(id, cartItemDTO.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("Invalid ID", ENTITY_NAME, "idinvalid"));
        }

        if (!cartItemRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("Entity not found", ENTITY_NAME, "idnotfound"));
        }
        Optional<CartItemDTO> result = cartItemService.partialUpdate(cartItemDTO);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Patch successfully", result));
    }

    /**
     * {@code GET  /cart-items} : get all the cartItems.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cartItems in body.
     */
    @GetMapping("/cart-items")
    public ResponseEntity<List<CartItemDTO>> getAllCartItems(CartItemCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CartItems by criteria: {}", criteria);
        Page<CartItemDTO> page = cartItemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cart-items/count} : count all the cartItems.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cart-items/count")
    public ResponseEntity<Long> countCartItems(CartItemCriteria criteria) {
        log.debug("REST request to count CartItems by criteria: {}", criteria);
        return ResponseEntity.ok().body(cartItemQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cart-items/:id} : get the "id" cartItem.
     *
     * @param id the id of the cartItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cartItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cart-items/{id}")
    public ResponseEntity<CartItemDTO> getCartItem(@PathVariable Long id) {
        log.debug("REST request to get CartItem : {}", id);
        Optional<CartItemDTO> cartItemDTO = cartItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cartItemDTO);
    }

    /**
     * {@code DELETE  /cart-items/:id} : delete the "id" cartItem.
     *
     * @param id the id of the cartItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cart-items/{id}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long id) {
        log.debug("REST request to delete CartItem : {}", id);
        cartItemService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert("LolApp", true, ENTITY_NAME, id.toString()))
                .build();
    }

    /**
     * Add and change quantity with productId
     */
    @PreAuthorize("hasRole('ADMIN') or #username == authentication.name")
    @PutMapping("cart-items/add/{username}")
    public ResponseEntity<ResponseObject> addQuantity(@PathVariable String username,  @RequestBody CartItemDTO cartItemDTO) {
        log.info("Add quantity of product: {}", cartItemDTO.getProductId());
        CartItemDTO cartItemDTO1 = cartItemService.updateQuantity(cartItemDTO,username);
        if(cartItemDTO1 == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("NOT OK", "Add failure" , cartItemDTO1)
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "Add success" , cartItemDTO1)
            );
        }
    }

}
