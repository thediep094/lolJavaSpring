package com.example.lol.lol.services.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;


@NoArgsConstructor
@AllArgsConstructor
public class ProductImageDTO implements Serializable {

    private Long id;

    private String url;

    private Long productId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductImageDTO)) {
            return false;
        }

        ProductImageDTO productImageDTO = (ProductImageDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productImageDTO.id);
    }


}
