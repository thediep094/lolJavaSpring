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
 * {@code /shiping-addresses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ShipingAddressCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter orderId;

    private StringFilter location;

    private StringFilter city;

    private StringFilter fullname;

    public ShipingAddressCriteria() {}

    public ShipingAddressCriteria(ShipingAddressCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.orderId = other.orderId == null ? null : other.orderId.copy();
        this.location = other.location == null ? null : other.location.copy();
        this.city = other.city == null ? null : other.city.copy();
        this.fullname = other.fullname == null ? null : other.fullname.copy();
    }

    @Override
    public ShipingAddressCriteria copy() {
        return new ShipingAddressCriteria(this);
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

    public StringFilter getLocation() {
        return location;
    }

    public StringFilter location() {
        if (location == null) {
            location = new StringFilter();
        }
        return location;
    }

    public void setLocation(StringFilter location) {
        this.location = location;
    }

    public StringFilter getCity() {
        return city;
    }

    public StringFilter city() {
        if (city == null) {
            city = new StringFilter();
        }
        return city;
    }

    public void setCity(StringFilter city) {
        this.city = city;
    }

    public StringFilter getFullname() {
        return fullname;
    }

    public StringFilter fullname() {
        if (fullname == null) {
            fullname = new StringFilter();
        }
        return fullname;
    }

    public void setFullname(StringFilter fullname) {
        this.fullname = fullname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ShipingAddressCriteria that = (ShipingAddressCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(orderId, that.orderId) &&
            Objects.equals(location, that.location) &&
            Objects.equals(city, that.city) &&
            Objects.equals(fullname, that.fullname)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderId, location, city, fullname);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShipingAddressCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (orderId != null ? "orderId=" + orderId + ", " : "") +
            (location != null ? "location=" + location + ", " : "") +
            (city != null ? "city=" + city + ", " : "") +
            (fullname != null ? "fullname=" + fullname + ", " : "") +
            "}";
    }
}
