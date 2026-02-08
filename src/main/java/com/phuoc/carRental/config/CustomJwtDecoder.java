package com.phuoc.carRental.config;

import com.nimbusds.jose.JOSEException;
import com.phuoc.carRental.dto.requests.IntrospectRequest;
import com.phuoc.carRental.service.AuthenticationService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;

@Component
public class CustomJwtDecoder implements JwtDecoder {
    private final ObjectProvider<AuthenticationService> authenticationServiceProvider;
    @Value("${jwt.signerKey}")
    private String signerKey;
    private NimbusJwtDecoder nimbusJwtDecoder = null;

    public CustomJwtDecoder(ObjectProvider<AuthenticationService> authenticationServiceProvider) {
        this.authenticationServiceProvider = authenticationServiceProvider;
    }

    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            var response = authenticationServiceProvider.getObject()
                    .introspect(IntrospectRequest.builder().token(token).build());

            if (!response.isValid()) {
                throw new BadJwtException("Token is expired or invalid");
            }
        } catch (JOSEException | ParseException e) {
            throw new BadJwtException("Malformed token", e);
        }

        if (nimbusJwtDecoder == null) {
            SecretKeySpec secretKeySpec =
                    new SecretKeySpec(signerKey.getBytes(), "HS512");
            nimbusJwtDecoder = NimbusJwtDecoder
                    .withSecretKey(secretKeySpec)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build();
        }

        return nimbusJwtDecoder.decode(token);
    }
}
