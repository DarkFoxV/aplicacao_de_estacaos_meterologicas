package com.resolveai.stations.services;

import com.resolveai.stations.dto.StationDTO;
import com.resolveai.stations.entities.Address;
import com.resolveai.stations.entities.Station;
import com.resolveai.stations.entities.exceptions.ObjectAlreadyExistsException;
import com.resolveai.stations.entities.exceptions.ObjectNotFoundException;
import com.resolveai.stations.repositories.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationService {

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private AddressService addressService;

    public List<Station> findAll() {
        return stationRepository.findAll();
    }

    public Station findById(Long id) {
        return stationRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Station not found: " + id));
    }

    public Station save(StationDTO stationDTO) {
        try {
            Address address = addressService.findByPostalCode(stationDTO.postalCode());
            Station station = new Station(null, stationDTO.installation(), address);
            return stationRepository.save(station);
        } catch (DataIntegrityViolationException e) {
            throw new ObjectAlreadyExistsException("Station already registered");
        }
    }

    public void update(Station Station) {
        stationRepository.save(Station);
    }

    public void delete(Long id) {
        stationRepository.deleteById(id);
    }


}
