package com.bwango.harvestsyncbackend.controller;

import com.bwango.harvestsyncbackend.dto.AuthRequest;
import com.bwango.harvestsyncbackend.dto.AuthResponse;
import com.bwango.harvestsyncbackend.entity.UserEntity;
import com.bwango.harvestsyncbackend.repository.UserRepository;
import com.bwango.harvestsyncbackend.security.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        UserEntity user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();
        String token = jwtUtils.generateToken(user);
        Long expiresAt = jwtUtils.extractExpiration(token).getTime();

        return ResponseEntity.ok(AuthResponse.builder()
                .token(token)
                .expiresAt(expiresAt)
                .userId(user.getUserId())
                .name(user.getName())
                .role(user.getRole())
                .permissions(new ArrayList<>(user.getRole().getPermissions()))
                .build());
    }
}
