package com.example.lol.lol.services.mapper;

import com.example.lol.lol.model.Cart;
import com.example.lol.lol.model.CartItem;
import com.example.lol.lol.model.OrderItem;
import com.example.lol.lol.services.dto.CartDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CartItemToOrderItemMapper extends EntityMapper<CartItem, OrderItem> {
}
