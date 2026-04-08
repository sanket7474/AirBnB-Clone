package com.example.airBnBClone.security;


import com.example.airBnBClone.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Date;

@Service
public class JWTService {

    @Value("${jwt.secret.key}")
    private String jwtSecretKy;

    /**
     * This method retrieves the secret key used for signing and verifying JWT tokens.
     * It converts the secret key string into a byte array and creates a SecretKey object using the
     * Keys.hmacShaKeyFor method from the JJWT library. This method ensures that the secret key is of sufficient length
     * for HMAC-SHA algorithms (at least 256 bits for HS256) and will throw an exception if the key is too short, ensuring that your key is secure.
     *
     * @return
     */
    private SecretKey getSecretKey() {
        // Ensure the secret key is of sufficient length for HMAC-SHA algorithms (at least 256 bits for HS256)
        // hmacShaKeyFor is a convenient method that will handle the key length requirements for you
        // It will throw an exception if the key is too short, so you can be confident that your key is secure
        return Keys.hmacShaKeyFor(jwtSecretKy.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * This method generates an access token for the given user. The access token contains the user's ID, email, and roles as claims.
     * The token is signed using the secret key and has a short expiration time (e.g., 10 minutes) to enhance security.
     *
     * @param user
     * @return
     */
    public String generateAccessToken(User user) {

        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("roles", user.getRoles())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 100000)) //
                .signWith(getSecretKey())
                .compact();
    }

    /**
     * This method generates a refresh token for the given user. The refresh token has a longer expiration time than
     * the access token, allowing the user to obtain a new access token without having to log in again.
     * The refresh token contains the same claims as the access token,
     * but it can be used to request a new access token when the current one expires.
     *
     * @param user
     * @return
     */
    public String generateRefreshToken(User user) {
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("roles", user.getRoles())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000*60*10)) // 10 minutes
                .signWith(getSecretKey())
                .compact();
    }

    /**
     * This method takes a JWT token as input and parses it to extract the user ID from the token's claims.
     * It uses the secret key to verify the token's signature and ensure that it is valid.
     * If the token is valid, it retrieves the subject (which is the user ID) from the claims and returns it as a Long value.
     *
     * @param token
     * @return
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return Long.valueOf(claims.getSubject());
    }

}
