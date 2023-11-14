package com.ssafy.rollinghealer.auth;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.rollinghealer.auth.model.TokenInfoDto;
import com.ssafy.rollinghealer.member.UserDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ClaimsBuilder;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TokenProvider {
	private String secretKey="testjwtkeysecretkeytesttesttesttest";
	
	private long accessTokenExipredTime=60*30L;
	private long refreshTokenExpiredTime=60*60*24*2L;
	private static final String AUTHORITIES_KEY = "Authorization";

	private Key key;

	private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
	@PostConstruct
	private void init() {
        byte[] keyBytes = this.secretKey.getBytes(StandardCharsets.UTF_8);
        this.key= Keys.hmacShaKeyFor(keyBytes);
    }
	
  public TokenInfoDto createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        Date now = new Date();
        
        String accessToken= Jwts.builder()
        	.subject(authentication.getName())
        	.issuedAt(now) // 토큰 발행 시간 정보
        	.claim("has_grade", authorities)
        	.expiration(new Date(now.getTime() + accessTokenExipredTime)) // set Expire Time
        	.signWith(key)  // 키
        	.compact();
        
        String refreshToken= Jwts.builder() 
        		.expiration(new Date(now.getTime() + refreshTokenExpiredTime)) // set Expire Time
        		.signWith(key)  // 키
        		.compact();
        
         return TokenInfoDto.builder()
        		 .accessToken(accessToken)
        		 .refreshToken(refreshToken)
        		 .build();
    }
	
  
  public String resolveToken(HttpServletRequest request) {
      return request.getHeader("Authorization");
  }
//	public String createAccessToken(String userPk,String userGrade) {
//        Claims claims = Jwts.claims().subject(userPk).build(); // JWT payload 에 저장되는 정보단위, 보통 여기서 user를 식별하는 값을 넣는다.
//        Date now = new Date();
//        return Jwts.builder()
//                .claims(claims) // 정보 저장
//                .issuedAt(now) // 토큰 발행 시간 정보
//                .claim("has_grade", userGrade)
//                .expiration(new Date(now.getTime() + accessTokenExipredTime)) // set Expire Time
//                .signWith(key)  // 키
//                .compact();
//    }
	// 토큰에서 회원 정보 추출
    public Claims parseClaims(String accessToken) {
        return Jwts.parser().verifyWith((SecretKey) key).build().parseSignedClaims(accessToken).getPayload();
    }
    
    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
    	 Claims claims = parseClaims(token);

         Collection<? extends GrantedAuthority> authorities =
                 Arrays.stream(claims.get("has_grade").toString().split(","))
                         .map(SimpleGrantedAuthority::new)
                         .collect(Collectors.toList());

         User principal = new User(claims.getSubject(), "", authorities);

         return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
	

	public boolean validateToken(String token) {
		 try {
	            Jws<Claims> claims = Jwts.parser().verifyWith((SecretKey) key).build().parseSignedClaims(token);
	            return !claims.getPayload().getExpiration().before(new Date());
	        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
	            
	            logger.info("잘못된 JWT 서명입니다.");
	        } catch (ExpiredJwtException e) {
	            
	            logger.info("만료된 JWT 토큰입니다.");
	            
	        } catch (UnsupportedJwtException e) {
	            
	            logger.info("지원되지 않는 JWT 토큰입니다.");
	        } catch (IllegalArgumentException e) {
	            
	            logger.info("JWT 토큰이 잘못되었습니다.");
	        }
	        return false;
	}

}
