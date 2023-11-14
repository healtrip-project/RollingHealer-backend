package com.ssafy.rollinghealer.auth.controller;

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

import com.ssafy.rollinghealer.auth.JwtFilter;
import com.ssafy.rollinghealer.auth.TokenProvider;
import com.ssafy.rollinghealer.auth.model.AccessTokenDto;
import com.ssafy.rollinghealer.auth.model.LoginDto;
import com.ssafy.rollinghealer.auth.model.TokenInfoDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/login")
    public ResponseEntity<AccessTokenDto> authLogin(@RequestBody LoginDto loginDto) {
    	System.out.println("로그");
    	System.out.println(loginDto);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUserId(), loginDto.getPassword());

        // authenticate 메소드가 실행이 될 때 CustomUserDetailsService class의 loadUserByUsername 메소드가 실행
        System.out.println(authenticationToken+"클남");
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // 해당 객체를 SecurityContextHolder에 저장하고
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // authentication 객체를 createToken 메소드를 통해서 JWT Token을 생성
        TokenInfoDto tokenInfoDto = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        // response header에 jwt token에 넣어줌
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + tokenInfoDto.getRefreshToken());

        // tokenDto를 이용해 response body에도 넣어서 리턴
        return new ResponseEntity<>(AccessTokenDto.builder().accessTokenDto(tokenInfoDto.getAccessToken()).build(), httpHeaders, HttpStatus.OK);
    }
}