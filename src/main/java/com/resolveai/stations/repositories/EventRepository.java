package com.resolveai.stations.repositories;

import com.resolveai.stations.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
