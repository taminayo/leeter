package com.taminayo.leeter.authservice.controller;

import com.taminayo.leeter.authservice.dto.AuthRequest;
import com.taminayo.leeter.authservice.dto.AuthResponse;
import com.taminayo.leeter.authservice.dto.UserCredentialRequest;
import com.taminayo.leeter.authservice.service.AuthService;
import jakarta.ws.rs.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("register")
    public ResponseEntity<String> registerUser(@RequestBody UserCredentialRequest userCredentialRequest) {
        return authService.registerUser(userCredentialRequest);
    }

    @PostMapping("token")
    public ResponseEntity<AuthResponse> generateToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return authService.generateToken(authRequest);
        }
        return new ResponseEntity<AuthResponse>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("validate")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String token, @RequestParam("username") String username) {
        return authService.validateToken(token, username);
    }

    @GetMapping("getUsername")
    public ResponseEntity<String> getUsername(@RequestHeader("Authorization") String token) {
        return authService.getUsername(token);
    }
}
