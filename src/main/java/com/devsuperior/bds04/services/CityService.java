package com.devsuperior.bds04.services;


import com.devsuperior.bds04.dto.CityDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.repositories.CityRepository;
import com.devsuperior.bds04.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CityService {

    @Autowired
    private CityRepository repository;

    @Transactional(readOnly = true)
    public Page<CityDTO> findAllPaged(Pageable pageable) {
        Page<City> list = repository.findAll(pageable);
        return list.map(item -> new CityDTO(item));
    }

    @Transactional(readOnly = true)
    public CityDTO findById(Long id) {
        Optional<City> cityOptional = repository.findById(id);
        City entity = cityOptional.orElseThrow(() -> new ResourceNotFoundException("Cidade não encontrada"));
        return new CityDTO(entity);
    }

    @Transactional
    public CityDTO create(CityDTO dto) {
        City entity = new City();
        entity.setName(dto.getName());
        entity = repository.save(entity);

        return new CityDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<CityDTO> findAllSortedByName() {
        List<City> cities = repository.findAll(Sort.by("name"));

        List<CityDTO> cityDTOs = cities.stream()
                .map(item -> new CityDTO(item)).collect(Collectors.toList());

        return cityDTOs;
    }
}
