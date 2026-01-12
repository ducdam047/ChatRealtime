package com.example.ChatRealtime.service.impl;

import com.example.ChatRealtime.dtos.requests.LoginRequest;
import com.example.ChatRealtime.dtos.responses.AuthenticationResponse;
import com.example.ChatRealtime.entities.User;
import com.example.ChatRealtime.enums.ErrorCode;
import com.example.ChatRealtime.exception.AppException;
import com.example.ChatRealtime.repositories.UserRepository;
import com.example.ChatRealtime.service.AuthenticationService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.signerKey}")
    private String SIGNER_KEY;

    @Override
    public AuthenticationResponse authenticate(LoginRequest request) {
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        var token = generateToken(user);

        if(!(passwordEncoder.matches(request.getPassword(), user.getPassword())))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader((JWSAlgorithm.HS512));

        Date now = new Date();
        Date expiration = new Date(now.getTime() + 6 * 60 * 60 * 1000);
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("chat_realtime.com")
                .issueTime(now)
                .expirationTime(expiration)
//                .claim("scope", buildScope())
                .build();
        Payload payload = new Payload(claimsSet.toJSONObject());

        JWSObject object = new JWSObject(header, payload);

        try {
            object.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return object.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }
}
