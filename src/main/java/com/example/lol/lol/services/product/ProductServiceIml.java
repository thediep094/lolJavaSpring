package com.example.lol.lol.services.product;

import com.example.lol.lol.Repositories.ProductImageRepository;
import com.example.lol.lol.Repositories.ProductRepository;
import com.example.lol.lol.Repositories.ProductTagRepository;
import com.example.lol.lol.Repositories.ProductThumbnailRepository;
import com.example.lol.lol.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ProductServiceIml implements ProductService{
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductImageRepository productImageRepository;

    @Autowired
    ProductThumbnailRepository productThumbnailRepository;

    @Autowired
    ProductTagRepository productTagRepository;


    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
    @Override
    public ProductImage saveProductImage(ProductImage productImage) {
        log.info("Saving new product image {} to the database", productImage.getUrl());
        return productImageRepository.save(productImage);
    }

    @Override
    public ProductThumbnail saveProductThumbnail(ProductThumbnail productThumbnail) {
        log.info("Saving new product thumbnail {} to the database", productThumbnail.getName());
        return productThumbnailRepository.save(productThumbnail);
    }

    @Override
    public ProductTag saveProductTag(ProductTag productTag) {
        log.info("Saving new product tag {} to the database", productTag.getName());
        return productTagRepository.save(productTag);
    }

//    @Override
//    public void addImagesToProduct(Long productId, List<ProductRequestDto.ProductImageDto> imageDtos) {
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
//
//        List<ProductImage> images = new ArrayList<>();
//        for (ProductRequestDto.ProductImageDto imageDto : imageDtos) {
//            ProductImage image = new ProductImage();
//            image.setName(imageDto.getName());
//            image.setProduct(product);
//            images.add(image);
//        }
//
//        product.getImages().addAll(images);
//        productRepository.save(product);
//    }
//    @Override
//    public void addThumbnailsToProduct(Long productId, List<ProductRequestDto.ProductThumbnailDto> thumbnailDtos) {
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
//
//        List<ProductThumbnail> thumbnails = new ArrayList<>();
//        for (ProductRequestDto.ProductThumbnailDto thumbnailDto : thumbnailDtos) {
//            ProductThumbnail thumbnail = new ProductThumbnail();
//            thumbnail.setName(thumbnailDto.getName());
//            thumbnail.setProduct(product);
//            thumbnails.add(thumbnail);
//        }
//
//        product.getThumbnails().addAll(thumbnails);
//        productRepository.save(product);
//    }
//    @Override
//    public void addTagsToProduct(Long productId, List<ProductRequestDto.ProductTagDto> tagDtos) {
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
//
//        List<ProductTag> tags = new ArrayList<>();
//        for (ProductRequestDto.ProductTagDto tagDto : tagDtos) {
//            ProductTag tag = new ProductTag();
//            tag.setName(tagDto.getName());
//            tag.setProduct(product);
//            tags.add(tag);
//        }
//
//        product.getTags().addAll(tags);
//        productRepository.save(product);
//    }
    @Override
    public Product getProduct(Long productId) {
        log.info("Fetching product {}", productId);
        Product fetchProduct = productRepository.findProductById(productId);
        return fetchProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products;
    }

    @Override
    public List<ProductTag> getAllProductTags() {
        List<ProductTag> productTags = productTagRepository.findAll();
        return productTags;
    }

    @Override
    public List<ProductImage> getAllProductImages() {
        List<ProductImage> productImages = productImageRepository.findAll();
        return productImages;
    }

    @Override
    public List<ProductThumbnail> getAllProductsThumbnails() {
        List<ProductThumbnail> productThumbnails = productThumbnailRepository.findAll();
        return productThumbnails;
    }

    @Override
    public Product updateProduct(Product product) {
        log.info("Update product {}", product.getName());
        return productRepository.save(product);
    }

    @Override
    public ProductImage updateProductImage(ProductImage productImage) {
        log.info("Update product image {}", productImage.getId());
        return productImageRepository.save(productImage);
    }

    @Override
    public ProductThumbnail updateProductThumbnail(ProductThumbnail productThumbnail) {
        log.info("Update product image {}", productThumbnail.getId());
        return productThumbnailRepository.save(productThumbnail);
    }

    @Override
    public ProductTag updateProductTag(ProductTag productTag) {
        log.info("Update product tag {}", productTag.getId());
        return productTagRepository.save(productTag);
    }

    @Override
    public void deleteProduct(Long productId) {
        log.info("Deleting product with ID: {}", productId);
        productRepository.deleteById(productId);
    }

    @Override
    public void deleteProductImage(Long productImageId) {

    }

    @Override
    public void deleteProductThumbnail(Long productThumbnailId) {

    }

    @Override
    public void deleteProductTag(Long productTagId) {

    }


    // check exists
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }
}
