package com.example.lol.lol.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A ShipingAddress.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ShipingAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "location")
    private String location;

    @Column(name = "city")
    private String city;

    @Column(name = "fullname")
    private String fullname;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShipingAddress)) {
            return false;
        }
        return id != null && id.equals(((ShipingAddress) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShipingAddress{" +
            "id=" + getId() +
            ", orderId=" + getOrderId() +
            ", location='" + getLocation() + "'" +
            ", city='" + getCity() + "'" +
            ", fullname='" + getFullname() + "'" +
            "}";
    }
}
