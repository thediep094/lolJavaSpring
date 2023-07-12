package com.example.lol.lol.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Required;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    @NotEmpty(message = "Name is required")
    private String name;

    @Type(type = "text")
    private String description;
    @NotNull(message = "Price is required")
    private Double price;
    private Double compareAtPrice;
    private Date estimatedShipDate;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<ProductImage> images = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    private List<ProductThumbnail> thumbnails = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    private List<ProductTag> tags = new ArrayList<>();


}
