package com.resolveai.stations.services;

import com.resolveai.stations.dto.AddressDTO;
import com.resolveai.stations.entities.Address;
import com.resolveai.stations.entities.City;
import com.resolveai.stations.entities.exceptions.ObjectAlreadyExistsException;
import com.resolveai.stations.entities.exceptions.ObjectNotFoundException;
import com.resolveai.stations.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private  AddressRepository addressRepository;

    @Autowired
    private CityService cityService;

    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    public Address findByPostalCode(String postalCode){
        return addressRepository.findByPostalCode(postalCode).orElseThrow(() -> new ObjectNotFoundException("Address not found: " + postalCode));
    }

    public Address findById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Address not found: " + id));
    }

    public Address save(AddressDTO addressDTO) {
        try {
            City city = cityService.findByName(addressDTO.city());
            Address address = new Address(
                    null,
                    addressDTO.postalCode(),
                    addressDTO.street(),
                    addressDTO.neighborhood(),
                    city,
                    addressDTO.number()
            );
            return addressRepository.save(address);
        } catch (DataIntegrityViolationException e) {
            throw new ObjectAlreadyExistsException("Address already registered");
        }
    }

    public void update(Address address) {
        addressRepository.save(address);
    }

    public void delete(Long id) {
        addressRepository.deleteById(id);
    }
}

