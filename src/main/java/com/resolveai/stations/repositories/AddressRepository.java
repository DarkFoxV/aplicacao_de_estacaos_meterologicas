package com.resolveai.stations.repositories;

import com.resolveai.stations.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByPostalCode(String postalCode);
}
