package com.epam.util;

import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {

	 private String SECRET_KEY = "secreate@123";

	    public String extractUsername(String token) {
	        Claims claims = extractClaims(token);
			return claims.getSubject();
	    }

	    private Claims extractClaims(String token) {
	        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	    }

	    public String generateToken(UserDetails userDetails) {
	        return createToken( userDetails.getUsername());
	    }

	    private String createToken( String subject) {
	        return Jwts.builder().setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 ))
	                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	    }

	    public Boolean validateToken(String token, UserDetails userDetails) {
	        final String username = extractUsername(token);
			return (username.equals(userDetails.getUsername()) && extractClaims(token).getExpiration().after(new Date()));
	    }
}
