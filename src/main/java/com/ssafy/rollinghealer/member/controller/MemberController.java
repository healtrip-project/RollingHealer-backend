package com.ssafy.rollinghealer.member.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	private final MemberService memberService;
	@GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request){
    	
    	Cookie[] cookies =request.getCookies();
    	if(cookies!=null) {
    		
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
    	}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	
    }
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> userDetail(@PathVariable("userId") String userId, HttpServletRequest request) throws Exception{
		
		UserDto userInfo=memberService.userInfo(userId);
		
		return new ResponseEntity<UserDto>(userInfo,HttpStatus.OK);
	}
	@GetMapping
	public ResponseEntity<List<UserDto>> userList() throws Exception{
		
		List<UserDto> userList=memberService.userList();
		
		return new ResponseEntity<List<UserDto>>(userList,HttpStatus.OK);
	}
	
	@PostMapping("/{userId}/uploadthumbnail")
	  public ResponseEntity<?> updateUserThumbnail(@RequestBody String uploadThumbnailFileUrl, @PathVariable("userId") String userId) {
		 UserDto user = new UserDto();
		 user.setUserId(userId);
		 user.setUserThumbnailFileUrl(uploadThumbnailFileUrl.replaceAll("\"", ""));
	      memberService.updateUserThumbnail(user);
		return new ResponseEntity<>(HttpStatus.OK);
	  }
	
}
