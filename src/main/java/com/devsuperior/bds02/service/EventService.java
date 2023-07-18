package com.devsuperior.bds02.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.CityRepositories;
import com.devsuperior.bds02.repositories.EventRepositories;
import com.devsuperior.bds02.service.exceptions.ResourceNotFoundException;

@Service
public class EventService {

	@Autowired
	private EventRepositories repository;
	
	@Autowired
	private CityRepositories cityRepository;

	@Transactional
	public EventDTO update(Long id, EventDTO dto) {
		try {
			Event entity = repository.getOne(id);
			copyToEntity(dto, entity);
			entity = repository.save(entity);
			return new EventDTO(entity);
			
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}
		
		private void copyToEntity(EventDTO dto, Event entity) {
			City city = cityRepository.getOne(dto.getCityId());
			entity.setName(dto.getName());
			entity.setDate(dto.getDate());
			entity.setUrl(dto.getUrl());
			entity.setCity(city);
			
		}

}
