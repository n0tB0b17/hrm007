package com._7.hr.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com._7.hr.dto.auth.JwtAuthenticationResponse;
import com._7.hr.dto.auth.LoginRequest;
import com._7.hr.security.JWTokenProvider;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/tenant/{tenantId}/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JWTokenProvider jwTokenProvider;

    public AuthController(AuthenticationManager authenticationManager, JWTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUserToTenant(@PathVariable String tenantId,
            @RequestBody @Valid LoginRequest loginRequest) {
        String tenantUsername = tenantId + "/" + loginRequest.getUsername();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(tenantUsername, loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwTokenProvider.generateToken(authentication);
        JwtAuthenticationResponse response = new JwtAuthenticationResponse(tenantId, token);
        return ResponseEntity.ok(response);
    }
}
