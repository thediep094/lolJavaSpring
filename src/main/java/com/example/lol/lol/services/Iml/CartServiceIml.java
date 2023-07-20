package com.example.lol.lol.services.Iml;

import com.example.lol.lol.Repositories.CartRepository;
import com.example.lol.lol.model.Cart;
import com.example.lol.lol.services.domain.CartItemService;
import com.example.lol.lol.services.domain.CartService;
import com.example.lol.lol.services.domain.ProductImageService;
import com.example.lol.lol.services.domain.ProductService;
import com.example.lol.lol.services.dto.*;
import com.example.lol.lol.services.mapper.CartMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class CartServiceIml implements CartService {


    private final CartRepository cartRepository;

    private final CartMapper cartMapper;

    private final CartItemService cartItemService;

    private final ProductService productService;

    private final ProductImageService productImageService;

    public CartServiceIml(CartRepository cartRepository, CartMapper cartMapper, CartItemService cartItemService, ProductService productService, ProductImageService productImageService) {
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
        this.cartItemService = cartItemService;
        this.productService = productService;
        this.productImageService = productImageService;
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
    public CartWithCartItemDTO findOne(String username) {
        log.debug("Request to get Cart: {}", username);
        Cart cart = cartRepository.findByUsername(username);
        CartDTO cartDTO = cartMapper.toDto(cart);
        List<CartItemDTO> cartItemDTOS = cartItemService.findAll(cartDTO.getId());
        List<CartItemWithProductDTO> cartItemWithProductDTOS = getProductFromCartItem(cartItemDTOS);
        CartWithCartItemDTO cartWithCartItemDTO = new CartWithCartItemDTO();
        cartWithCartItemDTO.setItems(cartItemWithProductDTOS);
        cartWithCartItemDTO.setCartDTO(cartDTO);
        return cartWithCartItemDTO;
    }

    public List<CartItemWithProductDTO> getProductFromCartItem (List<CartItemDTO> cartItemDTOS) {
        List<CartItemWithProductDTO> cartItemWithProductDTOS = new ArrayList<>();
        for(CartItemDTO cartItemDTO : cartItemDTOS) {
            Optional<ProductDTO> productDTOOptional = productService.findOne(cartItemDTO.getProductId());
            List<ProductImageDTO> image = productImageService.findAllByProductId(cartItemDTO.getProductId());
            if (productDTOOptional.isPresent()) {
                ProductDTO productDTO = productDTOOptional.get();
                CartItemWithProductDTO cartItemWithProductDTO = new CartItemWithProductDTO();
                cartItemWithProductDTO.setCartItemDTO(cartItemDTO);
                cartItemWithProductDTO.setProductDTO(productDTO);
                cartItemWithProductDTO.setImages(image);
                cartItemWithProductDTOS.add(cartItemWithProductDTO);
            }
        }
        return cartItemWithProductDTOS;
    }


    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cart : {}", id);
        cartRepository.deleteById(id);
    }

}
