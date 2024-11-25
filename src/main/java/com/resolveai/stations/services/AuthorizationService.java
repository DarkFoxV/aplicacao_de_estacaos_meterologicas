package com.resolveai.stations.services;

import com.resolveai.stations.entities.exceptions.ObjectNotFoundException;
import com.resolveai.stations.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        try {
            return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(""));
        } catch (UsernameNotFoundException e) {
            throw new ObjectNotFoundException("User not found: " + email);
        }
    }
}
