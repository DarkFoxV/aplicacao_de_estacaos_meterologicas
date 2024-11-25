package com.resolveai.stations.resources;

import com.resolveai.stations.dto.EmailDTO;
import com.resolveai.stations.dto.ResetPasswordDTO;
import com.resolveai.stations.dto.TokenDTO;
import com.resolveai.stations.dto.UserDTO;
import com.resolveai.stations.entities.User;
import com.resolveai.stations.security.TokenService;
import com.resolveai.stations.services.PasswordResetService;
import com.resolveai.stations.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/users")
public class UserResource {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    TokenService tokenService;

    @Autowired
    PasswordResetService passwordResetService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid UserDTO userDTO) {
        User user = userService.save(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody @Valid UserDTO userDTO) {
        try {
            User user = userService.findByEmail(userDTO.email());
            UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(userDTO.email(), userDTO.password());
            var auth = authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((User) auth.getPrincipal());
            return ResponseEntity.ok(new TokenDTO(token));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Invalid credentials");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> initiatePasswordReset(@Valid @RequestBody EmailDTO emailDTO) {
        passwordResetService.initiatePasswordReset(emailDTO.email());
        return ResponseEntity.ok("Password reset email sent");
    }

    @PostMapping("/reset-password/confirm")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @Valid @RequestBody ResetPasswordDTO resetPasswordDTO) {
        passwordResetService.resetPassword(token, resetPasswordDTO.password());
        return ResponseEntity.ok("Password reset successful");
    }
}
