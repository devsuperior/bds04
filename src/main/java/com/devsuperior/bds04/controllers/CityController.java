package com.devsuperior.bds04.controllers;

import com.devsuperior.bds04.dto.CityDTO;
import com.devsuperior.bds04.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "cities")
public class CityController {

    /**
     * @Todo
     * CRUD para eventos e cidades
     * Endpoint de login
     * Configuração OAUTH
     * Configuração spring security JWT
     */

    @Autowired
    private CityService service;

    @GetMapping
    public ResponseEntity<List<CityDTO>> findAll() {

        List<CityDTO> list = service.findAllSortedByName();

        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CityDTO> findById(@PathVariable Long id) {
        CityDTO city = service.findById(id);
        return ResponseEntity.ok().body(city);
    }

    @PostMapping
    public ResponseEntity<CityDTO> create(@RequestBody @Valid CityDTO dto) {
        dto = service.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

}
