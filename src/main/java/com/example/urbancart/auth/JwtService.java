package com.example.urbancart.auth;

import com.example.urbancart.auth.dto.AuthResponseDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  @Value("${application.security.jwt.secret-key}")
  private String secretKey;

  @Value("${application.security.jwt.refresh-secret-key}")
  private String refreshSecretKey;

  @Value("${application.security.jwt.token-expiration}")
  private long jwtExpiration;

  @Value("${application.security.jwt.refresh-token.expiration}")
  private long refreshExpiration;

  public String extractEmail(String token, boolean isRefreshToken) {
    var email = extractClaim(token, isRefreshToken, Claims::getSubject);
    if (email == null) {
      throw new IllegalArgumentException("Invalid token");
    }
    return email;
  }

  public <T> T extractClaim(
      String token, boolean isRefreshToken, Function<Claims, T> claimsResolver) {
    try {
      final Claims claims = extractAllClaims(token, isRefreshToken);
      return claimsResolver.apply(claims);
    } catch (Exception e) {
      return null;
    }
  }

  public AuthResponseDto generateTokens(UserDetails userDetails) {
    var accessToken = buildToken(new HashMap<>(), userDetails, jwtExpiration, false);
    var refreshToken = buildToken(new HashMap<>(), userDetails, refreshExpiration, true);
    return new AuthResponseDto(accessToken, refreshToken);
  }

  private String buildToken(
      Map<String, Object> extraClaims,
      UserDetails userDetails,
      long expiration,
      boolean isRefreshToken) {
    return Jwts.builder()
        .setClaims(extraClaims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(getSignInKey(isRefreshToken), SignatureAlgorithm.HS256)
        .compact();
  }

  public boolean isTokenValid(String token, UserDetails userDetails, boolean isRefreshToken) {
    final String username = extractEmail(token, isRefreshToken);
    return username.equals(userDetails.getUsername()) && !isTokenExpired(token, isRefreshToken);
  }

  private boolean isTokenExpired(String token, Boolean isRefreshToken) {
    return extractExpiration(token, isRefreshToken).before(new Date());
  }

  private Date extractExpiration(String token, Boolean isRefreshToken) {
    return extractClaim(token, isRefreshToken, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token, boolean isRefreshToken) {
    return Jwts.parserBuilder()
        .setSigningKey(getSignInKey(isRefreshToken))
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Key getSignInKey(boolean isRefreshToken) {
    byte[] keyBytes = Decoders.BASE64.decode(isRefreshToken ? refreshSecretKey : secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
