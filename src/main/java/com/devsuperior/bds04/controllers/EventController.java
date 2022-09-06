package com.devsuperior.bds04.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.bds04.dto.EventDTO;
import com.devsuperior.bds04.services.EventService;

@RestController
@RequestMapping(value = "/events")
public class EventController {

	@Autowired
	private EventService service;
	
	@GetMapping
	public ResponseEntity<Page<EventDTO>> findAll(Pageable pageable) {
		Page<EventDTO> list = service.findAllPaged(pageable);
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<EventDTO> findById(@PathVariable Long id) {
		EventDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping
	public ResponseEntity<EventDTO> insert(@Valid @RequestBody EventDTO dto) {
		EventDTO newDto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newDto.getId()).toUri();
		return ResponseEntity.created(uri).body(newDto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<EventDTO> update(@PathVariable Long id,@Valid @RequestBody EventDTO dto) {
		EventDTO newDto = service.update(id, dto);
		return ResponseEntity.ok().body(newDto);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
