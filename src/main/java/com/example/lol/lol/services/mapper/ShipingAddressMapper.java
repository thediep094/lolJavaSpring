package com.example.lol.lol.services.mapper;

import com.example.lol.lol.model.ShipingAddress;
import com.example.lol.lol.services.dto.ShipingAddressDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ShipingAddress} and its DTO {@link ShipingAddressDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ShipingAddressMapper extends EntityMapper<ShipingAddressDTO, ShipingAddress> {}
