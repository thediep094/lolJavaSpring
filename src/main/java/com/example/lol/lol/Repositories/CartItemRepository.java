package com.example.lol.lol.Repositories;

import com.example.lol.lol.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long>, JpaSpecificationExecutor<CartItem> {
    List<CartItem> findAllByCartId(Long cartId);
    CartItem findByCartIdAndProductId(Long cartId, Long productId);

    void deleteByCartId(Long cartId);
}
