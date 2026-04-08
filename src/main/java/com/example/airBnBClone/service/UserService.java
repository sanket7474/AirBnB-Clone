package com.example.airBnBClone.service;

import com.example.airBnBClone.dto.UserDTO;
import com.example.airBnBClone.entities.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


public interface UserService {

    public User getUserById(Long id);

    public User createUser(UserDTO userDto);

    User getUserByEmail(String email);
}
