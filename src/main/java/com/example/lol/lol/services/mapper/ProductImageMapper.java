package com.example.lol.lol.services.mapper;

import com.example.lol.lol.model.ProductImage;
import com.example.lol.lol.services.dto.ProductImageDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ProductImageMapper extends EntityMapper<ProductImageDTO, ProductImage> {}