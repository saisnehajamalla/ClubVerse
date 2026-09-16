package com.clubverse.clubverse_backend.config;

import com.clubverse.clubverse_backend.entity.Badge;
import com.clubverse.clubverse_backend.entity.User;
import com.clubverse.clubverse_backend.repository.BadgeRepository;
import com.clubverse.clubverse_backend.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("demo")
public class DemoDataInitializer implements ApplicationRunner {

    private static final String ADMIN_EMAIL = "demo-admin@clubverse.test";
    private static final String STUDENT_EMAIL = "demo-student@clubverse.test";
    private static final String BADGE_NAME = "Demo Contributor";

    private final UserRepository userRepository;
    private final BadgeRepository badgeRepository;
    private final PasswordEncoder passwordEncoder;

    public DemoDataInitializer(UserRepository userRepository,
                               BadgeRepository badgeRepository,
                               PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.badgeRepository = badgeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        createUserIfAbsent(
                ADMIN_EMAIL,
                "Demo Admin",
                "demo-admin",
                User.Role.PLATFORM_ADMIN
        );
        createUserIfAbsent(
                STUDENT_EMAIL,
                "Demo Student",
                "demo-student",
                User.Role.STUDENT
        );
        createBadgeIfAbsent();
    }

    private User createUserIfAbsent(String email,
                                    String name,
                                    String password,
                                    User.Role role) {
        return userRepository.findByEmail(email).orElseGet(() -> {
            User user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole(role);
            return userRepository.save(user);
        });
    }

    private void createBadgeIfAbsent() {
        if (badgeRepository.findByName(BADGE_NAME).isEmpty()) {
            badgeRepository.save(new Badge(
                    BADGE_NAME,
                    "Awarded for earning 50 demo points",
                    50L,
                    "contributor"
            ));
        }
    }
}
