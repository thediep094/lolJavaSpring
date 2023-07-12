package com.example.lol.lol.services.product;

import com.example.lol.lol.model.*;

import java.util.List;

public interface ProductService {
    // Create new
    Product saveProduct(Product product);
    ProductImage saveProductImage(ProductImage productImage);
    ProductThumbnail saveProductThumbnail(ProductThumbnail productThumbnail);
    ProductTag saveProductTag(ProductTag productTag);

    // merge data
//    void addImagesToProduct(Long productId, List<ProductRequestDto.ProductImageDto> productImageId);
//    void addThumbnailsToProduct(Long productId, List<ProductRequestDto.ProductThumbnailDto> productThumbnailId);
//    void addTagsToProduct(Long productId, List<ProductRequestDto.ProductTagDto> productTagId);

    //Get
    Product getProduct(Long productId);
    List<Product> getAllProducts();
    List<ProductTag> getAllProductTags();
    List<ProductImage> getAllProductImages();
    List<ProductThumbnail> getAllProductsThumbnails();

    // Update
    Product updateProduct(Product product);
    ProductImage updateProductImage(ProductImage productImage);
    ProductThumbnail updateProductThumbnail(ProductThumbnail productThumbnail);
    ProductTag updateProductTag(ProductTag productTag);

    // Delete
    void deleteProduct(Long productId);
    void deleteProductImage(Long productImageId);
    void deleteProductThumbnail(Long productThumbnailId);
    void deleteProductTag(Long productTagId);

    //check exists
    boolean existsByName(String name);

}
