package org.example.service;

import org.example.dto.UserSignupInputDTO;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // For encoding passwords

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(
                user.get().getUsername(),
                user.get().getPassword(),
                Collections.emptyList()
        );
    }


    public boolean createUser(UserSignupInputDTO signupInput) {
        // Check if user already exists by username or email
        if (userRepository.existsByUsername(signupInput.getUsername()) ||
                userRepository.existsByEmail(signupInput.getEmail())) {
            return false; // Or throw custom exception for duplicate user
        }

        // Map DTO to entity
        User user = new User();
        user.setUsername(signupInput.getUsername());
        user.setEmail(signupInput.getEmail());
        // Encode the password before saving
        user.setPassword(passwordEncoder.encode(signupInput.getPassword()));
        user.setFirstName(signupInput.getFirstName());
        user.setLastName(signupInput.getLastName());
        // Set other fields as needed

        // Save the user
        userRepository.save(user);

        return true;
    }
}
