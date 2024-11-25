package com.resolveai.stations.config;

import com.resolveai.stations.entities.*;
import com.resolveai.stations.entities.enums.Level;
import com.resolveai.stations.entities.enums.UserRoles;
import com.resolveai.stations.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Configuration
@Profile({"dev","prod"})
public class DataConfig implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private RegisterRepository registerRepository;

    @Autowired
    private EventRepository eventRepository;

    @Override
    public void run(String... args) throws Exception {
        Country brazil = new Country(null, "Brazil");
        countryRepository.save(brazil);

        City city1 = new City(null, "São Paulo", brazil);
        City city2 = new City(null, "Rio de Janeiro", brazil);
        City city3 = new City(null, "Salvador", brazil);
        City city4 = new City(null, "Brasília", brazil);
        City city5 = new City(null, "Curitiba", brazil);
        City city6 = new City(null, "Fortaleza", brazil);
        City city7 = new City(null, "Belo Horizonte", brazil);
        City city8 = new City(null, "Manaus", brazil);
        City city9 = new City(null, "Recife", brazil);
        City city10 = new City(null, "Porto Alegre", brazil);
        cityRepository.saveAll(List.of(city1, city2, city3, city4, city5, city6, city7, city8, city9, city10));

        Address address1 = new Address(null, "01000-000", "Av. Paulista", "Bela Vista", city1, 1000);
        Address address2 = new Address(null, "20000-000", "Av. Atlântica", "Copacabana", city2, 1500);
        Address address3 = new Address(null, "40000-000", "Praça da Sé", "Pelourinho", city3, 200);
        Address address4 = new Address(null, "70000-000", "Esplanada dos Ministérios", "Zona Cívico-Administrativa", city5, 500);
        Address address5 = new Address(null, "80000-000", "Rua XV de Novembro", "Centro", city5, 600);
        Address address6 = new Address(null, "60000-000", "Av. Beira Mar", "Meireles", city6, 700);
        Address address7 = new Address(null, "30000-000", "Av. Afonso Pena", "Centro", city7, 1200);
        Address address8 = new Address(null, "69000-000", "Av. Eduardo Ribeiro", "Centro", city8, 300);
        Address address9 = new Address(null, "50000-000", "Av. Boa Viagem", "Boa Viagem", city9, 150);
        Address address10 = new Address(null, "90000-000", "Av. Ipiranga", "Centro Histórico", city10, 100);


        addressRepository.saveAll(List.of(address1, address2, address3, address4, address5, address6, address7, address8, address9, address10));

        Station station1 = new Station(null, new Date(), address1);
        Station station2 = new Station(null, new Date(), address2);
        Station station3 = new Station(null, new Date(), address3);
        Station station4 = new Station(null, new Date(), address4);
        Station station5 = new Station(null, new Date(), address5);
        stationRepository.saveAll(List.of(station1, station2, station3, station4, station5));

        Event event1 = new Event(null, Level.INFO, "Station operational", new Date(), station1);
        Event event2 = new Event(null, Level.WARN,   "High temperature detected", new Date(), station2);
        Event event3 = new Event(null, Level.ERROR, "Station offline", new Date(), station3);
        Event event4 = new Event(null, Level.INFO, "Station operational", new Date(), station4);
        Event event5 = new Event(null, Level.WARN,   "High temperature detected", new Date(), station5);
        eventRepository.saveAll(List.of(event1, event2, event3, event4, event5));

        Register register1 = new Register(null, generateRandomValue(-10.0, 35.0), generateRandomValue(0.0, 100.0),
                generateRandomValue(0.0, 100.0), generateRandomValue(0.0, 50.0), new Date(), station1);

        Register register2 = new Register(null, generateRandomValue(-10.0, 35.0), generateRandomValue(0.0, 100.0),
                generateRandomValue(0.0, 100.0), generateRandomValue(0.0, 50.0), new Date(), station2);

        Register register3 = new Register(null, generateRandomValue(-10.0, 35.0), generateRandomValue(0.0, 100.0),
                generateRandomValue(0.0, 100.0), generateRandomValue(0.0, 50.0), new Date(), station3);

        Register register4 = new Register(null, generateRandomValue(-10.0, 35.0), generateRandomValue(0.0, 100.0),
                generateRandomValue(0.0, 100.0), generateRandomValue(0.0, 50.0), new Date(), station4);

        Register register5 = new Register(null, generateRandomValue(-10.0, 35.0), generateRandomValue(0.0, 100.0),
                generateRandomValue(0.0, 100.0), generateRandomValue(0.0, 50.0), new Date(), station5);

        registerRepository.saveAll(List.of(register1, register2, register3, register4, register5));

        String encryptedPassword = new BCryptPasswordEncoder().encode("12345@Mi");
        String encryptedEmail = new BCryptPasswordEncoder().encode("virtualmachinevx@gmail.com");
        User user = userRepository.save(new User(null, "Michel Pereira", encryptedEmail, encryptedPassword, UserRoles.ADMIN));

    }

    private static Double generateRandomValue(double min, double max) {
        Random rand = new Random();
        return (double) (Math.round((min + (max - min) * rand.nextDouble()) * 100.0)/100);
    }

}
