package com.example.lol.lol.services.Iml;

import com.example.lol.lol.Repositories.CartItemRepository;
import com.example.lol.lol.Repositories.CartRepository;
import com.example.lol.lol.Repositories.OrderItemRepository;
import com.example.lol.lol.Repositories.OrderRepository;
import com.example.lol.lol.model.Cart;
import com.example.lol.lol.model.CartItem;
import com.example.lol.lol.model.Order;
import com.example.lol.lol.model.OrderItem;
import com.example.lol.lol.services.domain.OrderService;

import java.util.List;
import java.util.Optional;

import com.example.lol.lol.services.dto.OrderDTO;
import com.example.lol.lol.services.mapper.CartItemToOrderItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.lol.lol.services.mapper.OrderMapper;
/**
 * Service Implementation for managing {@link Order}.
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final OrderMapper orderMapper;

    private final CartItemToOrderItemMapper cartItemToOrderItemMapper;


    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository, CartRepository cartRepository, CartItemRepository cartItemRepository, OrderMapper orderMapper, CartItemToOrderItemMapper cartItemToOrderItemMapper) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderMapper = orderMapper;
        this.cartItemToOrderItemMapper = cartItemToOrderItemMapper;
    }

    @Override
    public OrderDTO save(OrderDTO orderDTO) {
        log.debug("Request to save Order : {}", orderDTO);
        Order order = orderMapper.toEntity(orderDTO);
        order = orderRepository.save(order);
        OrderDTO result = orderMapper.toDto(order);

        Cart cart = cartRepository.findByUsername(orderDTO.getUsername());
        List<CartItem> cartItems = cartItemRepository.findAllByCartId(cart.getId());

        for ( CartItem cartItem : cartItems) {
            OrderItem orderItem = cartItemToOrderItemMapper.toEntity(cartItem);
            if (orderItem != null) {
                orderItem.setOrderid(order.getId());
                orderItemRepository.save(orderItem);
            }

            cartItemRepository.deleteByCartId(cartItem.getId());

        }

        return result;
    }

    @Override
    public Optional<OrderDTO> partialUpdate(OrderDTO orderDTO) {
        log.debug("Request to partially update Order : {}", orderDTO);

        return orderRepository
            .findById(orderDTO.getId())
            .map(
                existingOrder -> {
                    orderMapper.partialUpdate(existingOrder, orderDTO);

                    return existingOrder;
                }
            )
            .map(orderRepository::save)
            .map(orderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Orders");
        return orderRepository.findAll(pageable).map(orderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrderDTO> findOne(Long id) {
        log.debug("Request to get Order : {}", id);
        return orderRepository.findById(id).map(orderMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Order : {}", id);
        orderRepository.deleteById(id);
    }
}
