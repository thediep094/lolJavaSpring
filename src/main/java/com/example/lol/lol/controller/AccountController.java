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
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccountController {
    private final AccountService accountService;

    // Create Account
    @PostMapping("/create/accounts")
    public ResponseEntity<ResponseObject> createAccount(@RequestBody Account request) {
        // Check if the account with the given username already exists
        if (accountService.accountExists(request.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("Bad Request", "Account with this username already exists", null)
            );
        }

        // Set any other required fields for the account

        // Save the account to the database
        Account createdAccount = accountService.saveAccount(request);
        accountService.addRoleToAccount(request.getUsername(), "ROLE_USER");

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseObject("Created", "Account created successfully", createdAccount)
        );
    }

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
