package com.example.lol.lol.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductThumbnail {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private Long productId;
}
