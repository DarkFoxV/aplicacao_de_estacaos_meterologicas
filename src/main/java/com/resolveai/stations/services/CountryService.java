package com.resolveai.stations.services;


import com.resolveai.stations.dto.CountryDTO;
import com.resolveai.stations.entities.Country;
import com.resolveai.stations.entities.exceptions.ObjectAlreadyExistsException;
import com.resolveai.stations.entities.exceptions.ObjectNotFoundException;
import com.resolveai.stations.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    @Autowired
    private  CountryRepository countryRepository;

    public List<Country> findAll() {
        return countryRepository.findAll();
    }

    public Country findById(Long id) {
        return countryRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Country not found: " + id));
    }

    public Country findByName(String name){
        return countryRepository.findByName(name).orElseThrow(() -> new ObjectNotFoundException("Country not found: " + name));
    }

    public Country save(CountryDTO countryDTO) {
        try {
            Country country = new Country(null,countryDTO.name());
            return countryRepository.save(country);
        } catch (DataIntegrityViolationException e) {
            throw new ObjectAlreadyExistsException("Country already exists: " + countryDTO.name());
        }
    }

    public void update(Country Country) {
        countryRepository.save(Country);
    }

    public void delete(Long id) {
        countryRepository.deleteById(id);
    }
}
