package com.example.lol.lol.controller;

import com.example.lol.lol.Repositories.OrderItemRepository;
import com.example.lol.lol.model.ResponseObject;
import com.example.lol.lol.services.criteria.OrderItemCriteria;
import com.example.lol.lol.services.domain.OrderItemService;
import com.example.lol.lol.services.dto.OrderItemDTO;
import com.example.lol.lol.services.query.OrderItemQueryService;
import lombok.AllArgsConstructor;
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
@RequiredArgsConstructor
@Slf4j
public class OrderItemController {
    private static final String ENTITY_NAME = "order item";

    private String applicationName= "LOLapp";

    private final OrderItemService orderItemService;

    private final OrderItemRepository orderItemRepository;

    private final OrderItemQueryService orderItemQueryService;

    /**
     * {@code POST  /order-items} : Create a new orderItem.
     *
     * @param orderItemDTO the orderItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderItemDTO, or with status {@code 400 (Bad Request)} if the orderItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-items")
    public ResponseEntity<ResponseObject> createOrderItem(@Valid @RequestBody OrderItemDTO orderItemDTO) throws URISyntaxException {
        log.debug("REST request to save OrderItem : {}", orderItemDTO);
        if (orderItemDTO.getId() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("A new order cannot already have an ID", ENTITY_NAME, "idexists")
            );
        }
        OrderItemDTO result = orderItemService.save(orderItemDTO);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject("OK", "Create success", result)
        );
    }

    /**
     * {@code PUT  /order-items/:id} : Updates an existing orderItem.
     *
     * @param id the id of the orderItemDTO to save.
     * @param orderItemDTO the orderItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderItemDTO,
     * or with status {@code 400 (Bad Request)} if the orderItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-items/{id}")
    public ResponseEntity<ResponseObject> updateOrderItem(
            @PathVariable(value = "id", required = false) final Long id,
            @Valid @RequestBody OrderItemDTO orderItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrderItem : {}, {}", id, orderItemDTO);
        if (orderItemDTO.getId() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Invalid id", ENTITY_NAME, "idnull"));
        }
        if (!Objects.equals(id, orderItemDTO.getId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Invalid id", ENTITY_NAME, "idinvalid"));
        }

        if (!orderItemRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Entity not found", ENTITY_NAME, "idnotfound"));
        }

        OrderItemDTO result = orderItemService.save(orderItemDTO);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, null))
                .body(new ResponseObject("ok", "success", result));
    }

    /**
     * {@code PATCH  /order-items/:id} : Partial updates given fields of an existing orderItem, field will ignore if it is null
     *
     * @param id the id of the orderItemDTO to save.
     * @param orderItemDTO the orderItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderItemDTO,
     * or with status {@code 400 (Bad Request)} if the orderItemDTO is not valid,
     * or with status {@code 404 (Not Found)} if the orderItemDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the orderItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/order-items/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ResponseObject> partialUpdateOrderItem(
            @PathVariable(value = "id", required = false) final Long id,
            @NotNull @RequestBody OrderItemDTO orderItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrderItem partially : {}, {}", id, orderItemDTO);
        if (orderItemDTO.getId() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Invalid id", ENTITY_NAME, "idnull"));
        }
        if (!Objects.equals(id, orderItemDTO.getId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Invalid id", ENTITY_NAME, "idinvalid"));
        }

        if (!orderItemRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Entity not found", ENTITY_NAME, "idnotfound"));
        }

        Optional<OrderItemDTO> result = orderItemService.partialUpdate(orderItemDTO);

        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, null))
                .body(new ResponseObject("ok", "success", result));
    }

    /**
     * {@code GET  /order-items} : get all the orderItems.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderItems in body.
     */
    @GetMapping("/order-items")
    public ResponseEntity<List<OrderItemDTO>> getAllOrderItems(OrderItemCriteria criteria, Pageable pageable) {
        log.debug("REST request to get OrderItems by criteria: {}", criteria);
        Page<OrderItemDTO> page = orderItemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /order-items/count} : count all the orderItems.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/order-items/count")
    public ResponseEntity<Long> countOrderItems(OrderItemCriteria criteria) {
        log.debug("REST request to count OrderItems by criteria: {}", criteria);
        return ResponseEntity.ok().body(orderItemQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /order-items/:id} : get the "id" orderItem.
     *
     * @param id the id of the orderItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-items/{id}")
    public ResponseEntity<OrderItemDTO> getOrderItem(@PathVariable Long id) {
        log.debug("REST request to get OrderItem : {}", id);
        Optional<OrderItemDTO> orderItemDTO = orderItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderItemDTO);
    }

    /**
     * {@code DELETE  /order-items/:id} : delete the "id" orderItem.
     *
     * @param id the id of the orderItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-items/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        log.debug("REST request to delete OrderItem : {}", id);
        orderItemService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }

}
