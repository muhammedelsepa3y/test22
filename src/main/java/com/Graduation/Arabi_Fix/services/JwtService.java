package com.Graduation.Arabi_Fix.services;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtService
{

    private  SecretKey secretKey;

    @PostConstruct
    private Key publishKSecretKey()
    {

        secretKey = Jwts.SIG.HS256.key().build();
        return secretKey;
    }

    public String generateToken(UserDetails userDetails)
    {
        return generateToken(new HashMap<>(),userDetails);
    }

    public String generateToken(Map<String, Object>extraClaims, UserDetails userDetails)
    {
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(secretKey).compact();
    }

    public Claims decodeJWT(String jwt)
    {

        return Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }
    public String extractUsername(String token)
    {
        return extractClaim(token,Claims::getSubject);
    }

    private Date extractExpirationDate(String token)
    {
        return extractClaim(token,Claims::getExpiration);
    }
    public <T> T extractClaim(String token, Function<Claims,T> claimsTFunction){
        final Claims claims = getAllClaims(token);
        return claimsTFunction.apply(claims);
    }
    private Claims getAllClaims(String token)
    {
        return Jwts.parser().verifyWith(secretKey)
                .build().parseClaimsJws(token).getBody();
    }

    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }
    private boolean isTokenExpired(String token)
    {
        return extractExpirationDate(token).before(new Date());
    }
}
