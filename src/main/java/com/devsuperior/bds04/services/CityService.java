package com.devsuperior.bds04.services;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.devsuperior.bds04.dto.CityDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.repositories.CityRepository;

@Service
public class CityService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private CityRepository repository;
	
	public List<CityDTO> findAll(){
		List<City> list = repository.findAll(Sort.by("name"));
		return list.stream().map(city -> new CityDTO(city)).collect(Collectors.toList());
	}
	
    public CityDTO insert(CityDTO dto) {
    	City entity = new City();
    	copyDtoToEntity(entity, dto);
    	entity = repository.save(entity);
    	return new CityDTO(entity);
    }
    
    private void copyDtoToEntity(City entity, CityDTO dto) {
    	entity.setName(dto.getName());
    }
	
}
