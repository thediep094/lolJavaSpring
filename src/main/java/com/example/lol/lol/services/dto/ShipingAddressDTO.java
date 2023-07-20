package com.example.lol.lol.services.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;


public class ShipingAddressDTO implements Serializable {

    private Long id;

    @NotNull
    private Long orderId;

    private String location;

    private String city;

    private String fullname;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShipingAddressDTO)) {
            return false;
        }

        ShipingAddressDTO shipingAddressDTO = (ShipingAddressDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, shipingAddressDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShipingAddressDTO{" +
            "id=" + getId() +
            ", orderId=" + getOrderId() +
            ", location='" + getLocation() + "'" +
            ", city='" + getCity() + "'" +
            ", fullname='" + getFullname() + "'" +
            "}";
    }
}
