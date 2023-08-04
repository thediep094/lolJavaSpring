package com.example.lol.lol.database;


import com.example.lol.lol.Repositories.*;
import com.example.lol.lol.model.*;
import com.example.lol.lol.services.domain.AccountService;
import com.example.lol.lol.services.domain.CartItemService;
import com.example.lol.lol.services.domain.CartService;
import com.example.lol.lol.services.domain.ProductService;
import com.example.lol.lol.services.dto.CartDTO;
import com.example.lol.lol.services.dto.CartItemDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Date;

@Configuration
@Slf4j
public class Database {

    @Autowired
    AccountService accountService;

    @Autowired
    ProductService productService;

    @Autowired
    CartService cartService;

    @Autowired
    CartItemService cartItemService;


    @Bean
    CommandLineRunner initDatabase(AccountRepository accountRepository,
                                   ProductRepository productRepository,
                                   ProductImageRepository productImageRepository,
                                   CartItemRepository cartItemRepository){
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {

//                ACCOUNT
                accountService.saveRole(new Role(null, "ROLE_ADMIN"));
                accountService.saveRole(new Role(null, "ROLE_USER"));

                accountService.saveAccount(new Account(null, "admin", "admin", "admin", "admin", "https://ddragon-webp.lolmath.net/latest/img/profileicon/5710.webp", "mail", new ArrayList<>()));
                accountService.saveAccount(new Account(null, "test", "test", "test", "test", "https://ddragon-webp.lolmath.net/latest/img/profileicon/5710.webp", "mail",new ArrayList<>()));

                accountService.addRoleToAccount("admin", "ROLE_ADMIN");
                accountService.addRoleToAccount("test", "ROLE_USER");


//                PRODUCT
                Product product = new Product();
                product.setName("Product 1");
                product.setDescription("Product 1 Description");
                product.setPrice(10.0);
                product.setCompareAtPrice(15.0);
                productRepository.save(product);


//                PRODUCT IMAGE

                productImageRepository.save(new ProductImage(null, "this/is/url", 5L));

                //Cart
                cartService.save(new CartDTO(null, "test"));

                //Cart item
                cartItemRepository.save(new CartItem(null, 1L, 5L, 20));
            }
        };
    }
}
