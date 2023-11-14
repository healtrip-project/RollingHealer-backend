package com.ssafy.rollinghealer.auth.model.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.rollinghealer.auth.model.UserAuthDto;
import com.ssafy.rollinghealer.auth.model.mapper.AuthMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {
	private final AuthMapper authMapper;
	private PasswordEncoder passwordEncoder;
	
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
    	UserAuthDto userDetail=authMapper.selectOneAuthenticationByUserName(userId);
    	if(userDetail==null) {
    		throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");    		
    	}
    	return userDetail;
        //return createUserDetails(userDetail);
        
    }
}