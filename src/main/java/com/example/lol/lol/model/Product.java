package com.example.lol.lol.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    @Type(type = "text")
    private String description;
    @NotNull(message = "Price is required")

    @Column(name = "price")
    private Double price;

    @Column(name = "compare_at_price")
    private Double compareAtPrice;

    @Column(name = "estimated_ship_date")
    private Date estimatedShipDate;



}
