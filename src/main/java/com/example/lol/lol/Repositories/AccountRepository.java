package com.example.lol.lol.Repositories;

import com.example.lol.lol.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findTopByUsername(String username);
}
