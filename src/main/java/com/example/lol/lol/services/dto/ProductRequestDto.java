package com.example.lol.lol.services.dto;


import com.example.lol.lol.services.dto.ProductImageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public class ProductRequestDto {
        private String name;
        private String description;
        private Double price;
        private Double compareAtPrice;
        private Date estimatedShipDate;
        private List<ProductImageDTO> images;

        // Constructors, getters, and setters
        @NoArgsConstructor
        @AllArgsConstructor
        @Data
        public static class ProductImageDto {
            private String name;

        }

    }
