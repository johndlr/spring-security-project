package com.juandlr.springsecurityproject.service.jwt;

import com.juandlr.springsecurityproject.constants.JwtConstants;
import com.juandlr.springsecurityproject.exception.RoleNameNotFoundException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final Environment environment;

    public String jwtTokenGenerator(Authentication authentication){
        String userName = authentication.getName();
        String role = authentication
                .getAuthorities()
                .stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElseThrow(() -> new RoleNameNotFoundException("No roles assigned to the user"));
        return Jwts
                .builder()
                .issuer("com.juandlr")
                .subject(userName)
                .claim("role",role)
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + 600000))
                .signWith(secretKeyGenerator())
                .compact();
    }

    public boolean jwtTokenValidator(String token){
        try {
            Claims claims = getClaimsFromToken(token);
            if (checkExpiration(claims)){
                throw new JwtException("Token has expired");
            }
            return true;
        }catch (JwtException exception){
            throw new JwtException(exception.getMessage());
        }
    }

    public String getUserNameFromToken(String token){
        Claims claims = Jwts
                .parser()
                .verifyWith(secretKeyGenerator())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    public Claims getClaimsFromToken(String token){
        try {
            return Jwts
                    .parser()
                    .verifyWith(secretKeyGenerator())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException exception) {
            throw new JwtException("Invalid Token");
        }
    }

    public boolean checkExpiration(Claims claims){
        Date expirationInformation = claims.getExpiration();
        return expirationInformation.before(new Date());
    }

    private SecretKey secretKeyGenerator(){
        String secret = environment.getProperty(JwtConstants.JWT_SECRET, JwtConstants.JWT_SECRET_DEFAULT_VALUE);
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

}
