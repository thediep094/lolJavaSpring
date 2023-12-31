package com.example.lol.lol.Repositories;

import com.example.lol.lol.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findTopByName(String name);
}
