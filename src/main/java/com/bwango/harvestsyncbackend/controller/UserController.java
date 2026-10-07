package com.bwango.harvestsyncbackend.controller;

import com.bwango.harvestsyncbackend.entity.UserEntity;
import com.bwango.harvestsyncbackend.model.Role;
import com.bwango.harvestsyncbackend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static class UserDto {
        private String userId;
        private String username;
        private String password;
        private String name;
        private Role role;

        public UserDto() {}
        public UserDto(String userId, String username, String password, String name, Role role) {
            this.userId = userId;
            this.username = username;
            this.password = password;
            this.name = name;
            this.role = role;
        }
        public static UserDtoBuilder builder() { return new UserDtoBuilder(); }
        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public Role getRole() { return role; }
        public void setRole(Role role) { this.role = role; }

        public static class UserDtoBuilder {
            private String userId;
            private String username;
            private String password;
            private String name;
            private Role role;
            public UserDtoBuilder userId(String userId) { this.userId = userId; return this; }
            public UserDtoBuilder username(String username) { this.username = username; return this; }
            public UserDtoBuilder password(String password) { this.password = password; return this; }
            public UserDtoBuilder name(String name) { this.name = name; return this; }
            public UserDtoBuilder role(Role role) { this.role = role; return this; }
            public UserDto build() { return new UserDto(userId, username, password, name, role); }
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto dto) {
        UserEntity user = UserEntity.builder()
                .userId("usr_" + UUID.randomUUID().toString().substring(0, 8))
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .role(dto.getRole())
                .build();

        UserEntity savedUser = userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserDto.builder()
                .userId(savedUser.getUserId())
                .username(savedUser.getUsername())
                .name(savedUser.getName())
                .role(savedUser.getRole())
                .build());
    }
}
