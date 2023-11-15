package com.ssafy.rollinghealer.member.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.rollinghealer.auth.TokenProvider;
import com.ssafy.rollinghealer.auth.model.RefreshTokenAuthDto;
import com.ssafy.rollinghealer.auth.model.service.AuthService;
import com.ssafy.rollinghealer.member.model.UserDto;
import com.ssafy.rollinghealer.member.model.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class MemberController {
	private final AuthService authService;
	private final MemberService memberSerive;
	@GetMapping("/logout")
    public ResponseEntity<?> authJoin(HttpServletRequest request){
    	
    	Cookie[] cookies =request.getCookies();

    	for (Cookie cookie : cookies) {
			if("refresh_token".equals(cookie.getName())) {
				try {
					authService.removeRefreshToken(RefreshTokenAuthDto.builder()
							.refreshToken(cookie.getValue())
							.build());
					return new ResponseEntity<>(HttpStatus.OK);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	
    }
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> userDetail(@PathVariable("userId") String userId, HttpServletRequest request) throws Exception{
		
		UserDto userInfo=memberSerive.userInfo(userId);
		
		return new ResponseEntity<UserDto>(userInfo,HttpStatus.OK);
	}
	
}
