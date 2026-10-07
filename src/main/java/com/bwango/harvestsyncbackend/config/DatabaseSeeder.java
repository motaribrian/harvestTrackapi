package com.bwango.harvestsyncbackend.config;

import com.bwango.harvestsyncbackend.entity.UserEntity;
import com.bwango.harvestsyncbackend.model.Role;
import com.bwango.harvestsyncbackend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("superadmin").isEmpty()) {
            userRepository.save(UserEntity.builder()
                    .userId("usr_001")
                    .username("superadmin")
                    .password(passwordEncoder.encode("SuperAdmin123!"))
                    .name("System Super Admin")
                    .role(Role.SUPER_ADMIN)
                    .build());
        }
    }
}
