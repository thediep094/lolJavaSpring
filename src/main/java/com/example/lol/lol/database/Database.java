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


    @Bean
    CommandLineRunner initDatabase(){
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {

            }
        };
    }
}
