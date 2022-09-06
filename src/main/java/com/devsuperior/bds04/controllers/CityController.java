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

import com.devsuperior.bds04.dto.CityDTO;
import com.devsuperior.bds04.services.CityService;

@RestController
@RequestMapping(value = "/users")
public class CityController {
	
	@Autowired
	private CityService service;
	
	@GetMapping
	public ResponseEntity<Page<CityDTO>> findAll(Pageable pageable) {
		Page<CityDTO> list = service.findAllPaged(pageable);
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<CityDTO> findById(@PathVariable Long id) {
		CityDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping
	public ResponseEntity<CityDTO> insert(@Valid @RequestBody CityDTO dto) {
		CityDTO newDto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newDto.getId()).toUri();
		return ResponseEntity.created(uri).body(newDto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<CityDTO> update(@PathVariable Long id,@Valid @RequestBody CityDTO dto) {
		CityDTO newDto = service.update(id, dto);
		return ResponseEntity.ok().body(newDto);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
