package com.jeleniasty.betapp.auth;

import com.jeleniasty.betapp.features.user.BetappUser;
import com.jeleniasty.betapp.features.user.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  private static final String SECRET_KEY =
    "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

  public String extractUserEmail(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
    final Claims claims = extractAllClaims(token);
    return claimResolver.apply(claims);
  }

  public String generateToken(BetappUser betappUserDetails) {
    var extraClaims = Jwts.claims();
    extraClaims.put("username", betappUserDetails.getUsername());

    return generateToken(extraClaims, betappUserDetails);
  }

  public String generateToken(
    Map<String, Object> extraClaims,
    BetappUser betappUserDetails
  ) {
    return Jwts
      .builder()
      .setClaims(extraClaims)
      .setSubject(betappUserDetails.getEmail())
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 180))
      .signWith(getSignInKey(), SignatureAlgorithm.HS256)
      .compact();
  }

  public boolean isTokenValid(String token, UserPrincipal userPrincipal) {
    final String userEmail = extractUserEmail(token);
    return (
      (userEmail.equals(userPrincipal.getEmail())) && !isTokenExpired(token)
    );
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    return Jwts
      .parserBuilder()
      .setSigningKey(getSignInKey())
      .build()
      .parseClaimsJws(token)
      .getBody();
  }

  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
