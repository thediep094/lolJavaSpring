package com.example.lol.lol.services.domain;

import com.example.lol.lol.model.Account;
import com.example.lol.lol.model.Role;

import java.util.List;

public interface AccountService {
    Account saveAccount(Account account);
    Role saveRole(Role role);

    void addRoleToAccount(String username, String roleName);
    Boolean accountExists(String username);
    Account getAccount(String username);
    List<Account> getAccounts();
}
