package com.example.lol.lol.database;


import com.example.lol.lol.Repositories.*;
import com.example.lol.lol.model.*;
import com.example.lol.lol.services.account.AccountService;
import com.example.lol.lol.services.product.ProductService;
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


    @Bean
    CommandLineRunner initDatabase(AccountRepository accountRepository,
                                   ProductRepository productRepository,
                                   ProductImageRepository productImageRepository,
                                   ProductTagRepository productTagRepository,
                                   ProductThumbnailRepository productThumbnailRepository){
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {

//                ACCOUNT
                accountService.saveRole(new Role(null, "ROLE_ADMIN"));
                accountService.saveRole(new Role(null, "ROLE_USER"));

                accountService.saveAccount(new Account(null, "admin", "admin", "admin", "admin", "abc", new ArrayList<>()));
                accountService.saveAccount(new Account(null, "test", "test", "test", "test", "abc", new ArrayList<>()));

                accountService.addRoleToAccount("admin", "ROLE_ADMIN");
                accountService.addRoleToAccount("test", "ROLE_USER");


//                PRODUCT


            }
        };
    }
}
