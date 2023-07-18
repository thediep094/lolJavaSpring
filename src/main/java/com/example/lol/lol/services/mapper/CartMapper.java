package com.example.lol.lol.services.mapper;


import com.example.lol.lol.model.Cart;
import com.example.lol.lol.services.dto.CartDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CartMapper extends EntityMapper<CartDTO, Cart> {}
