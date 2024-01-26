package com.wonhyong.talk.security.jwt;

import com.wonhyong.talk.member.domain.MemberDetails;
import com.wonhyong.talk.member.service.MemberDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    @Value("${jwt.secret.key}")
    private String salt;

    @Value("${jwt.secret.token-expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.secret.refresh-token-expiration}")
    private long refreshTokenExpiration;


    private Key secretKey;

    private final MemberDetailsService memberDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(salt.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(MemberDetails member) {
        return generateToken(member, accessTokenExpiration);
    }

    public String generateRefreshToken(MemberDetails member) {
        return generateToken(member, refreshTokenExpiration);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Date extractIssuedAt(String token) {
        return extractClaim(token, Claims::getIssuedAt);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = memberDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }

    public String getUserPk(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        Claims claims = extractAllClaims(jwtToken);
        return !claims.getExpiration().before(new Date());
    }

    private String generateToken(MemberDetails member, long expiration) {
        Claims claims = Jwts.claims().setSubject(member.getUsername()); // JWT payload 에 저장되는 정보단위
        claims.put("role", member.getRole().toString());
        claims.put("id", String.valueOf(member.getId()));
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiration))
                .signWith(secretKey)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
