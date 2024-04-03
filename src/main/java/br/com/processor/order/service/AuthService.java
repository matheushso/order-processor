package br.com.processor.order.service;

import br.com.processor.order.exception.AuthException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class AuthService {

    private static final String BEARER = "Bearer";
    @Value("${jwt.secret}")
    private String secretKey;

    public String validateAuthAndDecode(String auth) {
        if (auth == null || auth.isBlank()) {
            throw new AuthException();
        }

        if (auth.startsWith(BEARER)) {
            auth = auth.substring(BEARER.length());
        }

        auth = auth.trim();
        return decodeJwtPayLoad(auth);
    }

    private String decodeJwtPayLoad(String auth) {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        Jws<Claims> claimsJws = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(keyBytes))
                .build()
                .parseSignedClaims(auth);

        String sellerId = claimsJws.getPayload().get("sellerId", String.class);

        if (sellerId == null || sellerId.isEmpty()) {
            throw new AuthException("Não foi informado sellerId no JWT, favor informar.");
        }

        return sellerId;
    }
}
