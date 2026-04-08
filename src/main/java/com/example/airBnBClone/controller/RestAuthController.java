package com.example.airBnBClone.controller;


import com.example.airBnBClone.dto.LoginDTO;
import com.example.airBnBClone.dto.LoginRes;
import com.example.airBnBClone.dto.UserDTO;
import com.example.airBnBClone.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class RestAuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody UserDTO userDto) {
        return new ResponseEntity<>(authService.signUp(userDto), org.springframework.http.HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String[]> signIn(@RequestBody LoginDTO loginDto) {
        String[] token =  authService.signIn(loginDto);

        LoginRes loginRes = new LoginRes();
        loginRes.setToken(token[0]);
        loginRes.setRefreshToken(token[1]);

        return new ResponseEntity<>(token, org.springframework.http.HttpStatus.OK);
    }
}
