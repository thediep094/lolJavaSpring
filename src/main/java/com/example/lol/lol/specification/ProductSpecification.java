package com.example.lol.lol.specification;


import com.example.lol.lol.model.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class ProductSpecification {

    public static Specification<Product> hasName(String name) {
        return (root, query, builder) ->
                builder.equal(root.get("name"), name);
    }

    public static Specification<Product> hasDescription(String description) {
        return (root, query, builder) ->
                builder.equal(root.get("description"), description);
    }

    public static Specification<Product> hasPrice(Double price) {
        return (root, query, builder) ->
                builder.equal(root.get("price"), price);
    }

    public static Specification<Product> hasCompareAtPrice(Double compareAtPrice) {
        return (root, query, builder) ->
                builder.equal(root.get("compareAtPrice"), compareAtPrice);
    }

    public static Specification<Product> hasEstimatedShipDate(Date estimatedShipDate) {
        return (root, query, builder) ->
                builder.equal(root.get("estimatedShipDate"), estimatedShipDate);
    }
}