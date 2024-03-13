package com.alligator.alligatorapi.service;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JwtService {
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    private final String USERNAME = "username";
    private final String AUTHORITIES = "authorities";
    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(String username, String authorities) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(USERNAME, username);
        claims.put(AUTHORITIES, authorities);

        return Jwts.builder()
                .signWith(signatureAlgorithm, secret)
                .setClaims(claims)
                .compact();
    }

    public boolean isValid(String jwtToken) {
        try {
            Jwts.parser().setSigningKey(secret).parse(jwtToken);
        } catch (JwtException e) {
            return false;
        }

        return true;
    }

    public String getUsername(String jwtToken) {

        Jws<Claims> jwt = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(jwtToken);

        return (String) (jwt.getBody()).get("username");
    }

    public List<SimpleGrantedAuthority> getAuthorities(String jwtToken) {
        Jws<Claims> jwt = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(jwtToken);

        String authoritiesString = (String) (jwt.getBody()).get("authorities");

        return Arrays.stream(authoritiesString.split(";"))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}