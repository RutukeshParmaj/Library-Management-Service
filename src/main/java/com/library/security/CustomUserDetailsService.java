package com.library.security;

import com.library.entity.User;
import com.library.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads a user by username for Spring Security authentication.
     * Username here is assumed to be the 'username' field in your User entity.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // You can also use findByEmail(username) if you want login with email
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username: " + username)
                );

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername()) // Login username
                .password(user.getPassword()) // Already encoded password in DB
                .roles(user.getRole()) // Example: ADMIN or USER
                .build();
    }
}
