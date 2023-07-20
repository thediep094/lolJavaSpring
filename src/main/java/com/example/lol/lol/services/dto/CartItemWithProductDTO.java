package com.example.lol.lol.services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemWithProductDTO {
    private CartItemDTO cartItemDTO;
    private ProductDTO productDTO;
    private List<ProductImageDTO> images;
}
