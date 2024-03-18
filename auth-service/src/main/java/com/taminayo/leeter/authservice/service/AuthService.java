package com.taminayo.leeter.authservice.service;

import com.taminayo.leeter.authservice.dto.AuthRequest;
import com.taminayo.leeter.authservice.dto.AuthResponse;
import com.taminayo.leeter.authservice.dto.UserCredentialRequest;
import com.taminayo.leeter.authservice.model.UserCredential;
import com.taminayo.leeter.authservice.repository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public ResponseEntity<String> registerUser(UserCredentialRequest userCredentialRequest) {
        if (userCredentialRepository.findByUsername(userCredentialRequest.getUsername()).isPresent() ||
            userCredentialRepository.findByEmail(userCredentialRequest.getEmail()).isPresent()) {
            return new ResponseEntity<>("username or email already exists", HttpStatus.BAD_REQUEST);
        }
        UserCredential userCredential = UserCredential.builder()
                .username(userCredentialRequest.getUsername())
                .password(passwordEncoder.encode(userCredentialRequest.getPassword()))
                .email(userCredentialRequest.getEmail())
                .build();
        userCredentialRepository.save(userCredential);
        return new ResponseEntity<>("registered successfully", HttpStatus.CREATED);
    }

    public ResponseEntity<AuthResponse> generateToken(AuthRequest authRequest) {
        String token = jwtUtil.generateToken(authRequest.getUsername());
        AuthResponse authResponse = AuthResponse.builder()
                .username(authRequest.getUsername())
                .token(token)
                .build();
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    public ResponseEntity<String> validateToken(String token) {
        return jwtUtil.validateToken(token.substring(7));
    }
}