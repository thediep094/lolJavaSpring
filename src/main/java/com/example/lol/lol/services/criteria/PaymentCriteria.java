package com.example.lol.lol.services.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /payments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PaymentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter orderId;

    private StringFilter cardNumber;

    private BooleanFilter status;

    public PaymentCriteria() {}

    public PaymentCriteria(PaymentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.orderId = other.orderId == null ? null : other.orderId.copy();
        this.cardNumber = other.cardNumber == null ? null : other.cardNumber.copy();
        this.status = other.status == null ? null : other.status.copy();
    }

    @Override
    public PaymentCriteria copy() {
        return new PaymentCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getOrderId() {
        return orderId;
    }

    public LongFilter orderId() {
        if (orderId == null) {
            orderId = new LongFilter();
        }
        return orderId;
    }

    public void setOrderId(LongFilter orderId) {
        this.orderId = orderId;
    }

    public StringFilter getCardNumber() {
        return cardNumber;
    }

    public StringFilter cardNumber() {
        if (cardNumber == null) {
            cardNumber = new StringFilter();
        }
        return cardNumber;
    }

    public void setCardNumber(StringFilter cardNumber) {
        this.cardNumber = cardNumber;
    }

    public BooleanFilter getStatus() {
        return status;
    }

    public BooleanFilter status() {
        if (status == null) {
            status = new BooleanFilter();
        }
        return status;
    }

    public void setStatus(BooleanFilter status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PaymentCriteria that = (PaymentCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(orderId, that.orderId) &&
            Objects.equals(cardNumber, that.cardNumber) &&
            Objects.equals(status, that.status)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderId, cardNumber, status);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (orderId != null ? "orderId=" + orderId + ", " : "") +
            (cardNumber != null ? "cardNumber=" + cardNumber + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            "}";
    }
}
