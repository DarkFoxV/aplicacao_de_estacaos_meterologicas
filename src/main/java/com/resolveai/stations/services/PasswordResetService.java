package com.resolveai.stations.services;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.resolveai.stations.entities.PasswordResetToken;
import com.resolveai.stations.entities.User;
import com.resolveai.stations.entities.exceptions.ObjectNotFoundException;
import com.resolveai.stations.repositories.PasswordResetTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.UUID;

@Service
public class PasswordResetService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    @Transactional
    public void initiatePasswordReset(String email) {
        User user = userService.findByEmail(email);

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken(token, user);
        tokenRepository.deleteAll();

        tokenRepository.save(resetToken);

        emailService.sendResetToken(email, token);
    }

    @Transactional
    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token).orElseThrow(() -> new ObjectNotFoundException("Invalid token"));

        if (resetToken.isExpired()) {
            tokenRepository.deleteById(resetToken.getId());
            throw new TokenExpiredException("Token has expired", resetToken.getExpiryDate().toInstant(ZoneOffset.UTC));
        }

        User user = resetToken.getUser();
        String encryptedPassword = new BCryptPasswordEncoder().encode(newPassword);
        user.setPassword(encryptedPassword);
        userService.update(user);

        tokenRepository.delete(resetToken);
    }
}