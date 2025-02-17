package com.example.bikash.SpringTest.JWT;

import com.example.bikash.SpringTest.Exceptions.JwtValidationException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.juli.logging.Log;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class JwtService {

    private static final String JWT_SECRET = "secrefadasfdasfdasf32542315dfgadsferdfsfadt4eqafdae4reaf4readfdt";
    private static final long ACCESS_TOKEN_EXPIRATION =24 * 60 * 1000; //  15 min
    private static final long REFRESH_TOKEN_EXPIRATION = 24 * 60 * 60 * 1000;


    public String generateToken(String username, boolean isAccessToken) {

        long expiration = isAccessToken ? ACCESS_TOKEN_EXPIRATION : REFRESH_TOKEN_EXPIRATION;

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(Keys.hmacShaKeyFor(JWT_SECRET.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }


    //get name from token

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(JWT_SECRET.getBytes())
                .build().parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    //validate
    public boolean validateToken(String token) throws JwtValidationException {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET.getBytes())
                    .build()
                    .parseClaimsJws(token);
            return true;

        } catch (ExpiredJwtException ex) {
            log.error("JWT Token is Expired: {}", ex.getMessage());
            throw new JwtValidationException("JWT is expired");
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT Token: {}", e.getMessage());
            throw new JwtValidationException("JWT is malformed");
        } catch (UnsupportedJwtException e) {
            log.error("JWT Token is Unsupported: {}", e.getMessage());
            throw new JwtValidationException("JWT is unsupported");
        } catch (IllegalArgumentException e) {
            log.error("JWT Token is Invalid: {}", e.getMessage());
            throw new JwtValidationException("JWT is invalid");
        }

    }
}


