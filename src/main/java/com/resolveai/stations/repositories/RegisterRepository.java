package com.resolveai.stations.repositories;

import com.resolveai.stations.entities.Register;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisterRepository extends JpaRepository<Register, Long> {
}
