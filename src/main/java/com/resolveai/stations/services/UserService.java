package com.resolveai.stations.services;

import com.resolveai.stations.dto.UserDTO;
import com.resolveai.stations.entities.User;
import com.resolveai.stations.entities.enums.UserRoles;
import com.resolveai.stations.entities.exceptions.ObjectAlreadyExistsException;
import com.resolveai.stations.entities.exceptions.ObjectNotFoundException;
import com.resolveai.stations.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("User not found: " + id));
    }

    public User findByEmail(String email) {
        return (User) userRepository.findByEmail(email).orElseThrow(() -> new ObjectNotFoundException("User not found: " + email));
    }

    public User save(UserDTO userDTO) {
        try {
            String encryptedPassword = new BCryptPasswordEncoder().encode(userDTO.password());
            User user = new User(null, userDTO.username(), userDTO.email(), encryptedPassword, UserRoles.USER);
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new ObjectAlreadyExistsException("Email already registered");
        }
    }

    public void update(User user) {
        userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }


}
