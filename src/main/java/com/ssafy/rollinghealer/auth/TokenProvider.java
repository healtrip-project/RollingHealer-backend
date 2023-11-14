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
import com.ssafy.rollinghealer.auth.model.AccessTokenDto;
import com.ssafy.rollinghealer.auth.model.RefreshTokenAuthDto;
import com.ssafy.rollinghealer.auth.model.TokenInfoDto;
import com.ssafy.rollinghealer.auth.model.service.AuthService;
import com.ssafy.rollinghealer.auth.model.service.CustomUserDetailService;
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
public class TokenProvider {
	private String secretKey="testjwtkeysecretkeytesttesttesttest";
	
	private static long accessTokenExipredTime=60*30L;
	private static long refreshTokenExpiredTime=60*60*24*3L;
	private final CustomUserDetailService userDetailsService;
	private final AuthService authService;
	
	public TokenProvider(CustomUserDetailService userDetailsService, AuthService authService) {
		super();
		this.userDetailsService = userDetailsService;
		this.authService = authService;
	}
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
        	.subject("access_token")
        	.claim("user_id",authentication.getName())
        	.issuedAt(now) // 토큰 발행 시간 정보
        	.claim("has_grade", authorities)
        	.expiration(new Date(now.getTime() + accessTokenExipredTime)) // set Expire Time
        	.signWith(key)  // 키
        	.compact();
        
        String refreshToken= Jwts.builder()
        		.subject("refresh_token")
        		.claim("user_id",authentication.getName())
        		.expiration(new Date(now.getTime() + getRefreshTokenExpiredTime())) // set Expire Time
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
	public AccessTokenDto createAccessToken(String userId,Collection<? extends GrantedAuthority> authorities) {
		String authoritiesString = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
		Date now = new Date();
		 return AccessTokenDto.builder().accessTokenDto(Jwts.builder()
		        	.subject("access_token")
		        	.claim("user_id",userId)
		        	.issuedAt(now)
		        	.claim("has_grade", authoritiesString)
		        	.expiration(new Date(now.getTime() + accessTokenExipredTime)) // set Expire Time
		        	.signWith(key)
		        	.compact()).build();
    }
	// 토큰에서 회원 정보 추출
    public Claims parseClaims(String token) {
        return Jwts.parser().verifyWith((SecretKey) key).build().parseSignedClaims(token).getPayload();
    }
    
    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
    	 Claims claims = parseClaims(token);

         Collection<? extends GrantedAuthority> authorities =
                 Arrays.stream(claims.get("has_grade").toString().split(","))
                         .map(SimpleGrantedAuthority::new)
                         .collect(Collectors.toList());

         User principal = new User(claims.get("user_id",String.class), "", authorities);

         return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

	public boolean validateToken(String token) {
		 try {
	            Claims claims = parseClaims(token);
	            return !claims.getExpiration().before(new Date());
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
	
	public boolean validateRefreshToken(String token) {
		try {
			Claims claims = parseClaims(token);
			return !claims.getExpiration().before(new Date());
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
	public UserDetails getAuthenticatedUser(String userId) {
		return userDetailsService.loadUserByUsername(userId);
	}

	public static int getRefreshTokenExpiredTime() {
		return (int) (refreshTokenExpiredTime>=Integer.MAX_VALUE?60*60*24*365:refreshTokenExpiredTime);
	}
	public boolean isFindRefreshToken(String refreshToken) {
		Claims claims=parseClaims(refreshToken);
		RefreshTokenAuthDto refreshTokenAuthDto = RefreshTokenAuthDto.builder()
				.userId(claims.get("user_id",String.class))
				.refreshToken(refreshToken)
				.build();
		int result;
		try {
			result = authService.findRefreshTokenByRefreshToken(refreshTokenAuthDto);
			return result>0;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("refreshToken finder error");
		}
		return false;
		
	}

}
