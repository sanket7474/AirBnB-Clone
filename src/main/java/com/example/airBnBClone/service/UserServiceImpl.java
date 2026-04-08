package com.example.airBnBClone.service;

import com.example.airBnBClone.dto.UserDTO;
import com.example.airBnBClone.entities.User;
import com.example.airBnBClone.enums.Role;
import com.example.airBnBClone.exception.ResourceNotFoundException;
import com.example.airBnBClone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    @Override
    public User getUserById(Long id) {

        log.info("Fetching user with id: {}", id);
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("User with id: " + id + " not found"));

        log.info("User fetched successfully with id: {}", id);

        return user;
    }

    @Override
    public User createUser(UserDTO userDto) {

        log.info("Creating user with email: {}", userDto.getEmail());

        userRepository.findByEmail(userDto.getEmail()).ifPresent(existingUser -> {
            throw new RuntimeException("User with email: " + userDto.getEmail() + " already exists");
        });

        User user = modelMapper.map(userDto, User.class);

        user.setRoles(List.of(Role.GUEST)); // Default role
        user = userRepository.save(user);

        log.info("User created successfully with email: {}", userDto.getEmail());

        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        log.info("Fetching user with email: {}", email);
        User user = (User) userRepository
                .findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User with email: " + email + " not found"));

        log.info("User fetched successfully with email: {}", email);

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
    }
}
