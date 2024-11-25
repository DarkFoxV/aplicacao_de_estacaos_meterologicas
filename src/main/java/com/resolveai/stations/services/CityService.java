package com.resolveai.stations.services;


import com.resolveai.stations.dto.CityDTO;
import com.resolveai.stations.entities.City;
import com.resolveai.stations.entities.Country;
import com.resolveai.stations.entities.exceptions.ObjectAlreadyExistsException;
import com.resolveai.stations.entities.exceptions.ObjectNotFoundException;
import com.resolveai.stations.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CountryService countryService;

    public List<City> findAll() {
        return cityRepository.findAll();
    }

    public City findById(Long id) {
        return cityRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("City not found: " + id));
    }

    public City findByName(String name){
        return cityRepository.findByName(name).orElseThrow(() -> new ObjectNotFoundException("City not found: " + name));
    }

    public City save(CityDTO cityDTO) {
        try {
            Country country = countryService.findById(cityDTO.id());
            City city = new City (null, cityDTO.name(), country);
            return cityRepository.save(city);
        } catch (DataIntegrityViolationException e) {
            throw new ObjectAlreadyExistsException("City already exists: " + cityDTO.name());
        }
    }

    public void update(City city) {
        cityRepository.save(city);
    }

    public void delete(Long id) {
        cityRepository.deleteById(id);
    }
}
