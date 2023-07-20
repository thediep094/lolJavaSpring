package com.example.lol.lol.services.mapper;

import com.example.lol.lol.model.OrderItem;
import com.example.lol.lol.services.dto.OrderItemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrderItem} and its DTO {@link OrderItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrderItemMapper extends EntityMapper<OrderItemDTO, OrderItem> {}
