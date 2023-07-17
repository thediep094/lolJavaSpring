package com.example.lol.lol.services.mapper;

import com.example.lol.lol.model.Product;
import com.example.lol.lol.services.dto.ProductDTO;
import com.example.lol.lol.services.dto.ProductRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ProductDTOMapper  extends EntityMapper<ProductRequestDto, ProductDTO> {}