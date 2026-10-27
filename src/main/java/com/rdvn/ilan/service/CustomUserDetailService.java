package com.rdvn.ilan.service;

import com.rdvn.ilan.entity.User;
import com.rdvn.ilan.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;



@Service
public class CustomUserDetailService implements UserDetailsService{

    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Kullanıcı bulunamadı");
        }

        User dbUser = user.get();

        return org.springframework.security.core.userdetails.User
                .withUsername(dbUser.getEmail())
                .password(dbUser.getPassword())
                .roles(dbUser.getRole().name())
                .build();


    }
}
