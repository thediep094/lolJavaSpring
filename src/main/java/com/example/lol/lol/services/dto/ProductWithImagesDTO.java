package com.example.lol.lol.services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductWithImagesDTO {
    private ProductDTO product;
    private List<ProductImageDTO> images;
}
