package com.college.erp.util;

import com.college.erp.model.User;
import com.college.erp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Check if admin user exists
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@college.edu");
            admin.setPasswordHash(passwordEncoder.encode("admin123"));
            admin.setRole(User.UserRole.ADMIN);
            admin.setStatus(User.UserStatus.ACTIVE);

            userRepository.save(admin);
            System.out.println("✅ Admin user seeded successfully");
        }

        // You can add more seed data here for other roles if needed
        createIfNotExists("accounts", "accounts@college.edu", "accounts123", User.UserRole.ACCOUNTS);
        createIfNotExists("admissions", "admissions@college.edu", "admissions123", User.UserRole.ADMISSIONS);
    }

    private void createIfNotExists(String username, String email, String password, User.UserRole role) {
        if (!userRepository.existsByUsername(username)) {
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPasswordHash(passwordEncoder.encode(password));
            user.setRole(role);
            user.setStatus(User.UserStatus.ACTIVE);

            userRepository.save(user);
            System.out.println("✅ " + role + " user seeded successfully");
        }
    }
}
