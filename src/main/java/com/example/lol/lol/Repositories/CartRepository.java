package com.example.lol.lol.Repositories;

import com.example.lol.lol.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long>, JpaSpecificationExecutor<Cart> {

    Optional<Cart> findByUsername(String username);

    boolean existsByUsername(String username);
}
