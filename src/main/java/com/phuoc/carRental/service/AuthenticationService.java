package com.phuoc.carRental.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.phuoc.carRental.common.enums.ErrorCode;
import com.phuoc.carRental.dto.requests.IntrospectRequest;
import com.phuoc.carRental.dto.requests.LoginRequest;
import com.phuoc.carRental.dto.requests.SignUpRequest;
import com.phuoc.carRental.dto.responses.IntrospectResponse;
import com.phuoc.carRental.dto.responses.LoginResponse;
import com.phuoc.carRental.dto.responses.UserProfileResponse;
import com.phuoc.carRental.exception.AppException;
import com.phuoc.carRental.mapper.UserMapper;
import com.phuoc.carRental.model.Permission;
import com.phuoc.carRental.model.Role;
import com.phuoc.carRental.model.User;
import com.phuoc.carRental.repository.RoleRepository;
import com.phuoc.carRental.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationService {

    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;
    final UserMapper userMapper;
    final RoleRepository roleRepository;

    final ConcurrentHashMap<String, Long> tokenBlacklist = new ConcurrentHashMap<>();

    @Value("${jwt.signerKey}")
    String signerKey;

    @Value("${jwt.valid-duration}")
    long jwtExpiration;

    public void signUp(SignUpRequest req) {
        if (userRepository.existsByEmailOrUsernameOrPhoneNum(
                req.getEmail(),
                req.getUsername(),
                req.getPhoneNum()
        )) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = userMapper.toSignUpEntity(req);
        user.setPassword(passwordEncoder.encode(req.getPassword()));

        Role adminRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));

        user.setRoles(Set.of(adminRole));

        userRepository.save(user);
    }

    @Transactional
    public LoginResponse signIn(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_CREDENTIALS));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.INVALID_CREDENTIALS);
        }
        user.setOnline(true);

        String token = generateToken(user);

        return LoginResponse.builder()
                .token(token)
                .build();
    }

    public String generateToken(User user) {
        try {
            Instant now = Instant.now();
            Instant expiresAt = now.plusSeconds(jwtExpiration);

            String roleNames = user.getRoles().stream()
                    .map(Role::getName)
                    .collect(Collectors.joining(","));

            String permissionNames = user.getRoles().stream()
                    .flatMap(role -> role.getPermissions().stream())
                    .map(Permission::getName)
                    .distinct()
                    .collect(Collectors.joining(","));

            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(user.getUsername())
                    .claim("userId", user.getId().toString())
                    .claim("roles", roleNames)
                    .claim("permissions", permissionNames)
                    .claim("avatar", user.getAvatar())
                    .issueTime(new Date(now.toEpochMilli()))
                    .expirationTime(new Date(expiresAt.toEpochMilli()))
                    .build();

            JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
            SignedJWT signedJWT = new SignedJWT(header, claimsSet);

            JWSSigner signer = new MACSigner(signerKey.getBytes());
            signedJWT.sign(signer);

            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new AppException(ErrorCode.INVALID_CREDENTIALS);
        }
    }

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        String token = request.getToken();

        try {
            SignedJWT signedJWT = SignedJWT.parse(token);

            JWSVerifier verifier = new MACVerifier(signerKey.getBytes());
            if (!signedJWT.verify(verifier)) {
                return IntrospectResponse.builder().valid(false).build();
            }

            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            if (expirationTime != null && new Date().after(expirationTime)) {
                return IntrospectResponse.builder().valid(false).build();
            }

            // Check if token is blacklisted
            if (tokenBlacklist.containsKey(token)) {
                return IntrospectResponse.builder().valid(false).build();
            }

            return IntrospectResponse.builder().valid(true).build();
        } catch (ParseException | JOSEException e) {
            return IntrospectResponse.builder().valid(false).build();
        }
    }

    @Transactional
    public void signOut(String username, String token) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        user.setOnline(false);
        userRepository.save(user);
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            if (expirationTime != null) {
                tokenBlacklist.put(token, expirationTime.getTime());
            }
        } catch (ParseException e) {
            throw new AppException(ErrorCode.INVALID_CREDENTIALS);
        }
    }

    @Transactional(readOnly = true)
    public UserProfileResponse getProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        return UserProfileResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .phoneNum(user.getPhoneNum())
                .avatar(user.getAvatar())
                .roles(user.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet()))
                .permissions(user.getRoles().stream()
                        .flatMap(role -> role.getPermissions().stream())
                        .map(Permission::getName)
                        .collect(Collectors.toSet()))
                .build();
    }

    @Scheduled(fixedRate = 60_000)
    public void cleanupBlacklist() {
        long now = System.currentTimeMillis();
        tokenBlacklist.entrySet()
                .removeIf(entry -> entry.getValue() < now);
    }
}
