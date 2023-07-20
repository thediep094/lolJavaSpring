package com.example.lol.lol.services.mapper;

import com.example.lol.lol.model.CartItem;
import com.example.lol.lol.services.dto.CartItemDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CartItemMapper extends EntityMapper<CartItemDTO, CartItem> {}
