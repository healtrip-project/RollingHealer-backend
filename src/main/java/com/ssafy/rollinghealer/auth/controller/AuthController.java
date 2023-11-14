package com.ssafy.rollinghealer.auth.controller;

import javax.servlet.http.Cookie;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.resource.HttpResource;

import com.ssafy.rollinghealer.auth.JwtFilter;
import com.ssafy.rollinghealer.auth.TokenProvider;
import com.ssafy.rollinghealer.auth.model.AccessTokenDto;
import com.ssafy.rollinghealer.auth.model.LoginDto;
import com.ssafy.rollinghealer.auth.model.TokenInfoDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    Logger logger = LoggerFactory.getLogger(AuthController.class);
    @PostMapping("/login")
    public ResponseEntity<AccessTokenDto> authLogin(@RequestBody LoginDto loginDto, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUserId(), loginDto.getPassword());
        logger.debug("인증 시작");
        // authenticate 메소드가 실행이 될 때 CustomUserDetailsService class의 loadUserByUsername 메소드가 실행	
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // 해당 객체를 SecurityContextHolder에 저장하고
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // authentication 객체를 createToken 메소드를 통해서 JWT Token을 생성
        TokenInfoDto tokenInfoDto = tokenProvider.createToken(authentication);

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
        		.build(), httpHeaders, HttpStatus.OK);
    }
}