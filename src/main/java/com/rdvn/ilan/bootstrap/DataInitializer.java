package com.rdvn.ilan.bootstrap;

import com.rdvn.ilan.enums.Role;
import com.rdvn.ilan.entity.User;
import com.rdvn.ilan.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

@Configuration
public class DataInitializer implements CommandLineRunner{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        Optional<User> user = userRepository.findByRole(Role.ADMIN);
        if (user.isEmpty()){
            User adminUser = new User();
            adminUser.setName("testFirstName");
            adminUser.setSurname("testLastName");
            adminUser.setEmail("test@gmail.com");
            adminUser.setPhone("11111");
            String password = "11111";
            String hPassword = passwordEncoder.encode(password);
            adminUser.setPassword(hPassword);
            adminUser.setRole(Role.ADMIN);
            adminUser.setCreatedAt(LocalDateTime.now());
            userRepository.save(adminUser);

        }
    }
}

