package com.devsuperior.bds04.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds04.dto.CityDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.repositories.CityRepository;
import com.devsuperior.bds04.services.exceptions.DataBaseException;
import com.devsuperior.bds04.services.exceptions.ResourceNotFoundException;

@Service
public class CityService {
	

	@Autowired
	private CityRepository repository;
	
	@Transactional(readOnly = true)
	public List<CityDTO> findAll() {

		List<City> listCities =repository.findAll(Sort.by("name"));
		return listCities.stream().map(c -> new CityDTO(c)).collect(Collectors.toList());
	}

	@Transactional
	public CityDTO insert(CityDTO cityRequest) {
		return new CityDTO(repository.save(new City( null,cityRequest.getName())));
		
	}

	public void delete(Long id) {
		try {
			repository.delete(this.findEntityExists(id));
		}catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Integrity violations");
		}
	}
	
	@Transactional(readOnly = true)
	public City findEntityExists(Long id) {
		Optional<City> obj = repository.findById(id);
		obj.orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));
		return obj.get();
	}


}
