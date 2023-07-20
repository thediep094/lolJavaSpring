package com.example.lol.lol.services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartWithCartItemDTO {

    private CartDTO cartDTO;
    private List<CartItemWithProductDTO> items;
}
