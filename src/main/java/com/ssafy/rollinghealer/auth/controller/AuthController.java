package com.ssafy.rollinghealer.auth.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.resource.HttpResource;

import com.ssafy.rollinghealer.auth.JwtFilter;
import com.ssafy.rollinghealer.auth.TokenProvider;
import com.ssafy.rollinghealer.auth.model.AccessTokenDto;
import com.ssafy.rollinghealer.auth.model.LoginDto;
import com.ssafy.rollinghealer.auth.model.RefreshTokenAuthDto;
import com.ssafy.rollinghealer.auth.model.TokenInfoDto;
import com.ssafy.rollinghealer.auth.model.mapper.AuthMapper;
import com.ssafy.rollinghealer.auth.model.service.AuthService;
import com.ssafy.rollinghealer.member.model.UserDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    @PostMapping("/refresh")
    public ResponseEntity<AccessTokenDto> requestAccessToken(@RequestBody LoginDto loginDto, HttpServletResponse response, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        log.debug("엑세스토큰 재발급");
        
        String refreshToken="";
		Cookie[] cookies = httpServletRequest.getCookies();
		if(cookies!=null) {
			for (Cookie cookie : cookies) {
				if("refresh_token".equals(cookie.getName())) {
					refreshToken=cookie.getValue();
				}
			}
    		if(StringUtils.hasText(refreshToken) 
    				&& tokenProvider.validateRefreshToken(refreshToken) 
    				&& tokenProvider.isFindRefreshToken(refreshToken)) {
    			UserDetails user = tokenProvider.getAuthenticatedUser(tokenProvider.parseClaims(refreshToken).get("user_id", String.class));
    			AccessTokenDto accessTokenDto=(tokenProvider.createAccessToken(user.getUsername(),user.getAuthorities()));
				httpServletResponse.setHeader(JwtFilter.AUTHORIZATION_HEADER, accessTokenDto.getAccessTokenDto());
				return new ResponseEntity<>(accessTokenDto, HttpStatus.CREATED);
    		}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    @PostMapping("/login")
    public ResponseEntity<AccessTokenDto> authLogin(@RequestBody LoginDto loginDto, HttpServletResponse response) {
        log.debug("인증 시작");
        
        TokenInfoDto tokenInfoDto = createAuthenticationToken(loginDto.getUserId(), loginDto.getPassword());

        HttpHeaders httpHeaders = new HttpHeaders();
        // response header에 jwt token에 넣어줌
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + tokenInfoDto.getAccessToken());
        Cookie cookie = new Cookie("refresh_token", tokenInfoDto.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setMaxAge(TokenProvider.getRefreshTokenExpiredTime());
//        cookie.setSecure(true); // https가 아니므로 아직 안됨
        response.addCookie(cookie);
        
        // tokenDto를 이용해 response body에도 넣어서 리턴
        return new ResponseEntity<>(AccessTokenDto.builder()
        		.accessTokenDto(tokenInfoDto
        		.getAccessToken())
        		.build(), httpHeaders, HttpStatus.CREATED);
    }
    
    @PostMapping("/join")
    public ResponseEntity<? extends AccessTokenDto> authJoin(@RequestBody UserDto userDto, HttpServletResponse response){
    	try {
    		String pass=userDto.getUserPassword();
			authService.userJoin(userDto);
			log.debug("회원가입 성공");
			TokenInfoDto tokenInfoDto=createAuthenticationToken(userDto.getUserId(), pass);
			HttpHeaders httpHeaders = new HttpHeaders();
	        // response header에 jwt token에 넣어줌
	        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + tokenInfoDto.getAccessToken());
	        Cookie cookie = new Cookie("refresh_token", tokenInfoDto.getRefreshToken());
	        cookie.setHttpOnly(true);
	        cookie.setMaxAge(TokenProvider.getRefreshTokenExpiredTime());
//	        cookie.setSecure(true); // https가 아니므로 아직 안됨
	        response.addCookie(cookie);
	        return new ResponseEntity<>(AccessTokenDto.builder()
	        		.accessTokenDto(tokenInfoDto.getAccessToken())
	        		.build(), httpHeaders, HttpStatus.CREATED);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	
    }
    
    public TokenInfoDto createAuthenticationToken(String userName,String userPassword) {
    	UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userName,userPassword);
        log.debug("인증 시작");
        // authenticate 메소드가 실행이 될 때 CustomUserDetailsService class의 loadUserByUsername 메소드가 실행	
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // 해당 객체를 SecurityContextHolder에 저장하고
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // authentication 객체를 createToken 메소드를 통해서 JWT Token을 생성
        TokenInfoDto tokenInfoDto = tokenProvider.createToken(authentication);
        try {
			authService.saveRefreshToken(RefreshTokenAuthDto.builder()
					.refreshToken(tokenInfoDto.getRefreshToken())
					.userId(userName)
					.expirationDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date().getTime()+TokenProvider.getRefreshTokenExpiredTime()))
					.build());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tokenInfoDto;
    }
}