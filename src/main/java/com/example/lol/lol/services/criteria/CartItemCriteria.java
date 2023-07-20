package com.example.lol.lol.services.criteria;

import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;

import java.io.Serializable;
import java.util.Objects;

public class CartItemCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter cartId;

    private LongFilter productId;

    private IntegerFilter quantity;

    public CartItemCriteria() {}

    public CartItemCriteria(CartItemCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.cartId = other.cartId == null ? null : other.cartId.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.quantity = other.quantity == null ? null : other.quantity.copy();
    }

    @Override
    public CartItemCriteria copy() {
        return new CartItemCriteria(this);
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

    public LongFilter getCartId() {
        return cartId;
    }

    public LongFilter cartId() {
        if (cartId == null) {
            cartId = new LongFilter();
        }
        return cartId;
    }

    public void setCartId(LongFilter cartId) {
        this.cartId = cartId;
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
        final CartItemCriteria that = (CartItemCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(cartId, that.cartId) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(quantity, that.quantity)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cartId, productId, quantity);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CartItemCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (cartId != null ? "cartId=" + cartId + ", " : "") +
            (productId != null ? "productId=" + productId + ", " : "") +
            (quantity != null ? "quantity=" + quantity + ", " : "") +
            "}";
    }
}
