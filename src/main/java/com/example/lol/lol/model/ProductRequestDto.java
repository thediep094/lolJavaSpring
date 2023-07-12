package com.example.lol.lol.model;


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
        private List<ProductImageDto> images;
        private List<ProductThumbnailDto> thumbnails;
        private List<ProductTagDto> tags;

        // Constructors, getters, and setters
        @NoArgsConstructor
        @AllArgsConstructor
        @Data
        public static class ProductImageDto {
            private String name;

        }
        @NoArgsConstructor
        @AllArgsConstructor
        @Data
        public static class ProductThumbnailDto {
            private String name;
        }
        @NoArgsConstructor
        @AllArgsConstructor
        @Data
        public static class ProductTagDto {
            private String name;
        }

    }
