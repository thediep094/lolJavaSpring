package com.example.lol.lol.services.mapper;

import com.example.lol.lol.model.Payment;
import com.example.lol.lol.services.dto.PaymentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Payment} and its DTO {@link PaymentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PaymentMapper extends EntityMapper<PaymentDTO, Payment> {}
