package com.example.lol.lol.controller;

import com.example.lol.lol.Repositories.PaymentRepository;
import com.example.lol.lol.model.ResponseObject;
import com.example.lol.lol.services.criteria.PaymentCriteria;
import com.example.lol.lol.services.domain.PaymentService;
import com.example.lol.lol.services.dto.PaymentDTO;
import com.example.lol.lol.services.query.PaymentQueryService;
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
public class PaymentController {
    private static final String ENTITY_NAME = "Payment";

    private String applicationName = "LolApp";

    private final PaymentService paymentService;

    private final PaymentRepository paymentRepository;

    private final PaymentQueryService paymentQueryService;

    /**
     * {@code POST  /payments} : Create a new payment.
     *
     * @param paymentDTO the paymentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentDTO, or with status {@code 400 (Bad Request)} if the payment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payments")
    public ResponseEntity<ResponseObject> createPayment(@Valid @RequestBody PaymentDTO paymentDTO) throws URISyntaxException {
        log.debug("REST request to save Payment : {}", paymentDTO);
        if (paymentDTO.getId() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("A new order cannot already have an ID", ENTITY_NAME, "idexists")
            );
        }
        PaymentDTO result = paymentService.save(paymentDTO);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject("OK", "Create successfully", result)
        );
    }

    /**
     * {@code PUT  /payments/:id} : Updates an existing payment.
     *
     * @param id the id of the paymentDTO to save.
     * @param paymentDTO the paymentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentDTO,
     * or with status {@code 400 (Bad Request)} if the paymentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payments/{id}")
    public ResponseEntity<ResponseObject> updatePayment(
            @PathVariable(value = "id", required = false) final Long id,
            @Valid @RequestBody PaymentDTO paymentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Payment : {}, {}", id, paymentDTO);
        if (paymentDTO.getId() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Invalid id", ENTITY_NAME, "idnull"));
        }
        if (!Objects.equals(id, paymentDTO.getId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Invalid id", ENTITY_NAME, "idinvalid"));
        }

        if (!paymentRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Entity not found", ENTITY_NAME, "idnotfound"));
        }

        PaymentDTO result = paymentService.save(paymentDTO);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, null))
                .body(new ResponseObject("ok", "success", result));
    }

    /**
     * {@code PATCH  /payments/:id} : Partial updates given fields of an existing payment, field will ignore if it is null
     *
     * @param id the id of the paymentDTO to save.
     * @param paymentDTO the paymentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentDTO,
     * or with status {@code 400 (Bad Request)} if the paymentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the paymentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the paymentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/payments/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ResponseObject> partialUpdatePayment(
            @PathVariable(value = "id", required = false) final Long id,
            @NotNull @RequestBody PaymentDTO paymentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Payment partially : {}, {}", id, paymentDTO);
        if (paymentDTO.getId() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Invalid id", ENTITY_NAME, "idnull"));
        }
        if (!Objects.equals(id, paymentDTO.getId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Invalid id", ENTITY_NAME, "idinvalid"));
        }

        if (!paymentRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("Entity not found", ENTITY_NAME, "idnotfound"));
        }

        Optional<PaymentDTO> result = paymentService.partialUpdate(paymentDTO);

        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, null))
                .body(new ResponseObject("ok", "success", result));
    }

    /**
     * {@code GET  /payments} : get all the payments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of payments in body.
     */
    @GetMapping("/payments")
    public ResponseEntity<List<PaymentDTO>> getAllPayments(PaymentCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Payments by criteria: {}", criteria);
        Page<PaymentDTO> page = paymentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /payments/count} : count all the payments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/payments/count")
    public ResponseEntity<Long> countPayments(PaymentCriteria criteria) {
        log.debug("REST request to count Payments by criteria: {}", criteria);
        return ResponseEntity.ok().body(paymentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /payments/:id} : get the "id" payment.
     *
     * @param id the id of the paymentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payments/{id}")
    public ResponseEntity<PaymentDTO> getPayment(@PathVariable Long id) {
        log.debug("REST request to get Payment : {}", id);
        Optional<PaymentDTO> paymentDTO = paymentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentDTO);
    }

    /**
     * {@code DELETE  /payments/:id} : delete the "id" payment.
     *
     * @param id the id of the paymentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payments/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        log.debug("REST request to delete Payment : {}", id);
        paymentService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }
}
