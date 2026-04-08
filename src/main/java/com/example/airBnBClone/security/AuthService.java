package com.example.airBnBClone.security;


import com.example.airBnBClone.dto.LoginDTO;
import com.example.airBnBClone.dto.UserDTO;
import com.example.airBnBClone.entities.User;
import com.example.airBnBClone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    public UserDTO signUp(UserDTO userDto) {

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = userService.createUser(userDto);

        return modelMapper.map(user, UserDTO.class);

    }

    public String[] signIn(LoginDTO loginDto) {
        String tokens[] = new String[2];
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getEmail(),
                    loginDto.getPassword()
            ));

            User user = (User) authentication.getPrincipal();

            tokens[0] = jwtService.generateAccessToken(user);
            tokens[1] = jwtService.generateRefreshToken(user);
        } catch (Exception e) {
            throw new RuntimeException("Invalid email or password");
        }
        return tokens;
    }
}
