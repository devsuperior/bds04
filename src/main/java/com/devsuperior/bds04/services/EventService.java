package com.devsuperior.bds04.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds04.dto.EventDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.entities.Event;
import com.devsuperior.bds04.repositories.EventRepository;
import com.devsuperior.bds04.services.exceptions.ResourceNotFoundException;

@Service
public class EventService {

	@Autowired
	private EventRepository repository;
	@Autowired
	private CityService cityService;
	
	@Transactional(readOnly = true)
	public Page<EventDTO> findAll(Pageable pageable) {

		Page<Event> listCities =repository.findAll(pageable);
		return listCities.map(e -> new EventDTO(e));
	}
	
	@Transactional
	public EventDTO update(EventDTO dto, Long id) {
		try {
			Event entity = this.findEventExists(id);
			City city = cityService.findEntityExists(dto.getCityId());
			entity.setCity(city);
			this.copyDtoToEntity(dto, entity);
			return new EventDTO(repository.save(entity));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}
	
	private Event findEventExists(Long id) {
		Optional<Event> obj = repository.findById(id);
		obj.orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));
		return obj.get();
	}
	
	private void copyDtoToEntity(EventDTO dto, Event entity) {
		entity.setName(dto.getName());
		entity.setDate(dto.getDate());
		entity.setUrl(dto.getUrl());
	}

	@Transactional
	public EventDTO insert(EventDTO eventRequest) {
		Event entity = new Event();
		City city = cityService.findEntityExists(eventRequest.getCityId());
		entity.setCity(city);
		this.copyDtoToEntity(eventRequest, entity);
		return new EventDTO(repository.save(entity));
		
	}
	
}
