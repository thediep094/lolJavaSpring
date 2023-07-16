package com.example.lol.lol.controller;

import com.example.lol.lol.model.Account;
import com.example.lol.lol.model.ResponseObject;
import com.example.lol.lol.services.domain.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccountController {
    @Autowired
    AccountService accountService;


    //USER
    @GetMapping("/user/account/{username}")
    public ResponseEntity<ResponseObject> getUser(@PathVariable String username) {
        // Lấy thông tin người dùng hiện tại
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Kiểm tra xem người dùng hiện tại có quyền truy cập vào tài khoản được yêu cầu hay không
        if (!currentUsername.equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    new ResponseObject("Forbidden", "Access denied", null)
            );
        }

        Account account = accountService.getAccount(username);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK", "Fetch success", account)
        );
    }


    // ADMIN
    @GetMapping("/admin/accounts")
    public ResponseEntity<ResponseObject> getAllUsers() {
        List<Account> accounts = accountService.getAccounts();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK", "Fetch success", accounts)
        );
    }

    @GetMapping("/admin/accounts/{username}")
    public ResponseEntity<ResponseObject> getUserAdmin(@PathVariable String username) {
        Account account = accountService.getAccount(username);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK", "Fetch success", account)
        );
    }

}
