package com.example.lol.lol.services.Iml;

import com.example.lol.lol.Repositories.AccountRepository;
import com.example.lol.lol.Repositories.RoleRepository;
import com.example.lol.lol.model.Account;
import com.example.lol.lol.model.Role;
import com.example.lol.lol.services.domain.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AccountServiceIml implements AccountService, UserDetailsService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        if( account == null) {
            log.error("Account not found in database");
            throw new UsernameNotFoundException("Account not found");
        } else {
            log.info("Account found in the database: {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        account.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(account.getUsername(), account.getPassword(), authorities);
    }

    @Override
    public Account saveAccount(Account account) {
        log.info("Saving new account {} to the database", account.getName());
        // using bCryptPasswordEncoder()
        String encodedPassword = bCryptPasswordEncoder.encode(account.getPassword());
        account.setPassword(encodedPassword);
        return accountRepository.save(account);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToAccount(String username, String roleName) {
        log.info("Adding role {} to account {}", roleName, username);
        Account account = accountRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        account.getRoles().add(role);
    }

    @Override
    public Account getAccount(String username) {
        log.info("Fetching account {}", username);
        return accountRepository.findByUsername(username);
    }

    @Override
    public List<Account> getAccounts() {
        log.info("Fetching all account");
        return accountRepository.findAll();
    }



}
