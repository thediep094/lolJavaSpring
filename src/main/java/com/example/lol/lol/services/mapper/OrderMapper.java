package com.example.lol.lol.services.mapper;

import com.example.lol.lol.model.Order;
import com.example.lol.lol.services.dto.OrderDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Order} and its DTO {@link OrderDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrderMapper extends EntityMapper<OrderDTO, Order> {}
