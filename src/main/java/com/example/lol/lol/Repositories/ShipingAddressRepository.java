package com.example.lol.lol.Repositories;

import com.example.lol.lol.model.ShipingAddress;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ShipingAddress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShipingAddressRepository extends JpaRepository<ShipingAddress, Long>, JpaSpecificationExecutor<ShipingAddress> {}
