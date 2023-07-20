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
 * {@code /order-items?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OrderItemCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter orderid;

    private LongFilter productId;

    private IntegerFilter quantity;

    public OrderItemCriteria() {}

    public OrderItemCriteria(OrderItemCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.orderid = other.orderid == null ? null : other.orderid.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.quantity = other.quantity == null ? null : other.quantity.copy();
    }

    @Override
    public OrderItemCriteria copy() {
        return new OrderItemCriteria(this);
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

    public LongFilter getOrderid() {
        return orderid;
    }

    public LongFilter orderid() {
        if (orderid == null) {
            orderid = new LongFilter();
        }
        return orderid;
    }

    public void setOrderid(LongFilter orderid) {
        this.orderid = orderid;
    }

    public LongFilter getProductId() {
        return productId;
    }

    public LongFilter productId() {
        if (productId == null) {
            productId = new LongFilter();
        }
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
    }

    public IntegerFilter getQuantity() {
        return quantity;
    }

    public IntegerFilter quantity() {
        if (quantity == null) {
            quantity = new IntegerFilter();
        }
        return quantity;
    }

    public void setQuantity(IntegerFilter quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OrderItemCriteria that = (OrderItemCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(orderid, that.orderid) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(quantity, that.quantity)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderid, productId, quantity);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderItemCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (orderid != null ? "orderid=" + orderid + ", " : "") +
            (productId != null ? "productId=" + productId + ", " : "") +
            (quantity != null ? "quantity=" + quantity + ", " : "") +
            "}";
    }
}
