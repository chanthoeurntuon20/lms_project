package com.amk.lms.utils;


import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtility implements Serializable {
    @Value("${jwt.issuer.name}")
    private String issuer;
    // retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }
    // retrieve username and aud from jwt token
    public JwtUserNameAndAud getUsernameAndAudFromToken(String token) {
        return new JwtUserNameAndAud(getClaimFromToken(token, Claims::getSubject), getClaimFromToken(token, Claims::getAudience));
    }
    //Retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimResolver.apply(claims);
    }
    // for retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().requireIssuer(issuer).setSigningKey(Constant.API_SECRET_KEY).parseClaimsJws(token).getBody();
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDetails userDetails, Collection<? extends GrantedAuthority> authentication, String aud) {
        Map<String, Object> claims = new HashMap<>();

        if (authentication != null && authentication.size() > 0)
            claims.put("roles", authentication);
        // claims.put("issuer",/*issuer*/"Go");
        return doGenerateToken(claims, userDetails.getUsername(), aud);
    }
    // do generate token
    private String doGenerateToken(Map<String, Object> claims, String subject, String aud) {
        return Jwts.builder().setClaims(claims).setSubject(subject)
                .setIssuer(issuer)
                .setAudience(aud)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Constant.TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS256, Constant.API_SECRET_KEY).compact();
    }
    //validate token
    public Boolean validateToken(String token, UserDetails userDetails, String aud) {
        final JwtUserNameAndAud jwtUserNameAndAud = getUsernameAndAudFromToken(token);
        return (jwtUserNameAndAud.getUsername().equals(userDetails.getUsername()) && !isTokenExpired(token) && jwtUserNameAndAud.getAud().equals(aud));

    }
    public UsernamePasswordAuthenticationToken getAuthenticationToken(final String token, final Authentication existingAuth, final UserDetails userDetails) {

        final JwtParser jwtParser = Jwts.parser().setSigningKey(Constant.API_SECRET_KEY);

        final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);

        final Claims claims = claimsJws.getBody();

        final Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(Constant.AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

}