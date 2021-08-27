package com.book.bookapi.configuration.jwt;

import com.book.bookapi.exceptions.JwtAuthenticationException;
import com.book.bookapi.exceptions.RefreshTokenInvalidException;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final UserDetailsService userDetailsService;

    @Value("${jwt.secret.access}")
    private String accessSecretKey;

    @Value("${jwt.secret.refresh}")
    private String refreshSecretKey;

    @Value("${jwt.expiration.access}")
    private int jwtValidity;

    @Value("${jwt.expiration.refresh}")
    private int refreshValidity;

    @Value("${jwt.header}")
    private String authorizationHeader;


    @PostConstruct
    protected void init(){
        accessSecretKey = Base64.getEncoder().encodeToString(accessSecretKey.getBytes());
        refreshSecretKey = Base64.getEncoder().encodeToString(refreshSecretKey.getBytes());
    }
    public String createToken(String email, String role){
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("role", role);
        Date now = new Date();
        Date validity = addHoursToJavaUtilDate(now, jwtValidity);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .setId("access")
                .signWith(SignatureAlgorithm.HS256, accessSecretKey)
                .compact();
    }

    public String createRefreshToken(String email, String role){
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("role", role);
        Date now = new Date();
        Date validity = addHoursToJavaUtilDate(now, refreshValidity);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .setId("refresh")
                .signWith(SignatureAlgorithm.HS256, refreshSecretKey)
                .compact();
    }

    public boolean validateToken(String token){
        try{
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(accessSecretKey).parseClaimsJws(token);
            if (!claimsJws.getBody().getId().equals("access"))
                throw new RefreshTokenInvalidException("Current token is not access token");
            return  !claimsJws.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e){
            throw new JwtAuthenticationException("Jwt token is expired or invalid");
        }
    }

    public boolean validateRefreshToken(String token){
        try{
            Jws<Claims> refreshToken = Jwts.parser().setSigningKey(refreshSecretKey).parseClaimsJws(token);
            if (!refreshToken.getBody().getId().equals("refresh"))
                throw new RefreshTokenInvalidException("Current token is not refresh token");
            return  !refreshToken.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e){
            throw new JwtAuthenticationException("Refresh token is expired or invalid");
        }
    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails,"", userDetails.getAuthorities());
    }

    public String getEmail(String token){
        try{
            return Jwts.parser().setSigningKey(accessSecretKey).parseClaimsJws(token).getBody().getSubject();
        }catch (ExpiredJwtException e){
            return e.getClaims().getSubject();
        }
    }

    public Jws<Claims> getToken(String token){
        return Jwts.parser().setSigningKey(accessSecretKey).parseClaimsJws(token);
    }

    public Jws<Claims> getRefreshToken(String token){
        if(validateRefreshToken(token))
            return Jwts.parser().setSigningKey(refreshSecretKey).parseClaimsJws(token);
        else return null;
    }

    public String resolveToken(HttpServletRequest request){
        return request.getHeader(authorizationHeader);
    }

    public Date addHoursToJavaUtilDate(Date date, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

}