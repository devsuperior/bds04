package com.devsuperior.bds04.services;

import com.devsuperior.bds04.dto.CityDTO;
import com.devsuperior.bds04.dto.EventDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.entities.Event;
import com.devsuperior.bds04.repositories.CityRepository;
import com.devsuperior.bds04.repositories.EventRepository;
import com.devsuperior.bds04.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private EventRepository repository;

    @Autowired
    private CityRepository cityRepository;

    @Transactional(readOnly = true)
    public Page<EventDTO> findAllPaged(Pageable pageable) {
        Page<Event> list = repository.findAll(pageable);
        return list.map(item -> new EventDTO(item));
    }

    @Transactional(readOnly = true)
    public EventDTO findById(Long id) {
        Optional<Event> cityOptional = repository.findById(id);
        Event entity = cityOptional.orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado"));
        return new EventDTO(entity);
    }

    @Transactional
    public EventDTO create(EventDTO dto) {
        if (dto.getCityId() == null) {
            throw new IllegalArgumentException("O ID da cidade não pode ser nulo");
        }

        Event entity = new Event();
        entity.setName(dto.getName());
        entity.setDate(dto.getDate());
        entity.setUrl(dto.getUrl());

        City city = cityRepository.findById(dto.getCityId())
                .orElseThrow(() -> new ResourceNotFoundException("Cidade não encontrada"));
        entity.setCity(city);
        entity = repository.save(entity);

        return new EventDTO(entity);
    }
}
