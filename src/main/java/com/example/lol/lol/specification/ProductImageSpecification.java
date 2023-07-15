package com.example.lol.lol.specification;

import com.example.lol.lol.model.ProductImage;
import org.springframework.data.jpa.domain.Specification;

public class ProductImageSpecification {
    public static Specification<ProductImage> hasUrl(String url) {
        return (root, query, builder) ->
                builder.equal(root.get("url"), url);
    }

    public static Specification<ProductImage> hasProductId(Long productId) {
        return (root, query, builder) ->
                builder.equal(root.get("productId"), productId);
    }
}
