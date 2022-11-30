package com.devsuperior.bds04.repositories;

import com.devsuperior.bds04.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
