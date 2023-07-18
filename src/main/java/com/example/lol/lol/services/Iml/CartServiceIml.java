package com.example.lol.lol.services.Iml;

import com.example.lol.lol.Repositories.CartRepository;
import com.example.lol.lol.model.Cart;
import com.example.lol.lol.services.domain.CartService;
import com.example.lol.lol.services.dto.CartDTO;
import com.example.lol.lol.services.mapper.CartMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@Slf4j
public class CartServiceIml implements CartService {


    private final CartRepository cartRepository;

    private final CartMapper cartMapper;

    public CartServiceIml(CartRepository cartRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
    }

    //Validator
    public Boolean checkExistUsername (String username) {
        if (cartRepository.existsByUsername(username)) {
            return true;
        }
        else {
            return false;
        }
    }


    @Override
    public CartDTO save(CartDTO cartDTO) {
        log.debug("Request to save Cart : {}", cartDTO);
        if (checkExistUsername(cartDTO.getUsername())) {
            return null;
        }
        Cart cart = cartMapper.toEntity(cartDTO);
        cart = cartRepository.save(cart);
        CartDTO result = cartMapper.toDto(cart);
        return result;
    }

    @Override
    public Optional<CartDTO> partialUpdate(CartDTO cartDTO) {
        log.debug("Request to partially update Cart : {}", cartDTO);

        return cartRepository
                .findById(cartDTO.getId())
                .map(
                        existingCart -> {
                            cartMapper.partialUpdate(existingCart, cartDTO);

                            return existingCart;
                        }
                )
                .map(cartRepository::save)
                .map(cartMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CartDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Carts");
        return cartRepository.findAll(pageable).map(cartMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CartDTO> findOne(String username) {
        log.debug("Request to get Cart : {}", username);
        return cartRepository.findByUsername(username).map(cartMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cart : {}", id);
        cartRepository.deleteById(id);
    }

}
