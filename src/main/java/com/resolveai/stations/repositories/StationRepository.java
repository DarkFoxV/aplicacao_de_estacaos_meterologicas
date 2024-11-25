package com.resolveai.stations.repositories;

import com.resolveai.stations.entities.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, Long> {
}
