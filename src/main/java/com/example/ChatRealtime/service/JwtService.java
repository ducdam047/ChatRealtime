package com.example.ChatRealtime.service;

import com.example.ChatRealtime.enums.ErrorCode;
import com.example.ChatRealtime.exception.AppException;
import com.example.ChatRealtime.repositories.UserRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.signerKey}")
    private String signerKey;
    private final UserRepository userRepository;

    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getUserIdFromToken(String token) {
        JWTClaimsSet claims = parseClaims(token);
        String username = claims.getSubject();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND))
                .getId();
    }

    private JWTClaimsSet parseClaims(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(signerKey.getBytes());
            if(!signedJWT.verify(verifier))
                throw new RuntimeException("Invalid JWT signature");

            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            if(claims.getExpirationTime().before(new Date()))
                throw new RuntimeException("JWT expired");

            return claims;
        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }
}
