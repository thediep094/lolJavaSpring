package com.example.lol.lol.services.Iml;

import com.example.lol.lol.Repositories.CartItemRepository;
import com.example.lol.lol.Repositories.CartRepository;
import com.example.lol.lol.model.Cart;
import com.example.lol.lol.model.CartItem;
import com.example.lol.lol.services.domain.CartItemService;
import com.example.lol.lol.services.dto.CartItemDTO;
import com.example.lol.lol.services.mapper.CartItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class CartItemServiceImpl implements CartItemService {

    private final Logger log = LoggerFactory.getLogger(CartItemServiceImpl.class);

    private final CartItemRepository cartItemRepository;

    private final CartItemMapper cartItemMapper;

    private final CartRepository cartRepository;

    private Long cartId;

    public CartItemServiceImpl(
            CartItemRepository cartItemRepository,
            CartItemMapper cartItemMapper,
            CartRepository cartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.cartItemMapper = cartItemMapper;
        this.cartRepository = cartRepository;
    }

    // Validate
    public Boolean checkExistProductId (CartItemDTO cartItemDTO) {
        if(cartItemDTO.getProductId() == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public CartItemDTO save(CartItemDTO cartItemDTO) {
        log.debug("Request to save CartItem : {}", cartItemDTO);
        CartItem cartItem = cartItemMapper.toEntity(cartItemDTO);
        cartItem = cartItemRepository.save(cartItem);
        CartItemDTO result = cartItemMapper.toDto(cartItem);
        return result;
    }

    @Override
    public CartItemDTO updateQuantity(CartItemDTO cartItemDTO, String username) {
        log.info("Request to update quantity of product: {}", cartItemDTO.getProductId());
        if(checkExistProductId(cartItemDTO)){
            return null;
        }
        log.info("username: {}", username);
        getCartId(username);
        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(this.cartId, cartItemDTO.getProductId());

        Integer currentQuantity = cartItem.getQuantity();
        cartItem.setQuantity(currentQuantity + cartItemDTO.getQuantity());
        CartItem newCartItem = cartItemRepository.save(cartItem);
        CartItemDTO cartItemDTO1 = cartItemMapper.toDto(newCartItem);
        return cartItemDTO1;
    }

    public void getCartId(String username) {
        Cart cartDTO = cartRepository.findByUsername(username);
        this.cartId = cartDTO.getId();
    }

    @Override
    public Optional<CartItemDTO> partialUpdate(CartItemDTO cartItemDTO) {
        log.debug("Request to partially update CartItem : {}", cartItemDTO);

        return cartItemRepository
            .findById(cartItemDTO.getId())
            .map(
                existingCartItem -> {
                    cartItemMapper.partialUpdate(existingCartItem, cartItemDTO);

                    return existingCartItem;
                }
            )
            .map(cartItemRepository::save)
            .map(cartItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CartItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CartItems");
        return cartItemRepository.findAll(pageable).map(cartItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartItemDTO> findAll(Long cartId) {
        log.debug("Request to get all CartItems");
        List<CartItem> cartItems = cartItemRepository.findAllByCartId(cartId);
        return cartItemMapper.toDto(cartItems);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CartItemDTO> findOne(Long id) {
        log.debug("Request to get CartItem : {}", id);
        return cartItemRepository.findById(id).map(cartItemMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CartItem : {}", id);
        cartItemRepository.deleteById(id);
    }




}
