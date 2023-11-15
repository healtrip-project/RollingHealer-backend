package com.ssafy.rollinghealer.auth.model.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.rollinghealer.auth.model.RefreshTokenAuthDto;
import com.ssafy.rollinghealer.auth.model.mapper.AuthMapper;
import com.ssafy.rollinghealer.member.model.UserDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	private final AuthMapper authMapper;
	private final PasswordEncoder passwordEncoder;

	@Override
	public int findRefreshTokenByRefreshToken(RefreshTokenAuthDto refreshTokenAuthDto) throws Exception {

		return authMapper.selectOneRefreshToekn(refreshTokenAuthDto);
	}

	@Override
	@Transactional
	public void userJoin(UserDto userDto) throws Exception {
		userDto.setUserPassword(passwordEncoder.encode(userDto.getUserPassword()));
		log.debug("userJoin");
		authMapper.insertUser(userDto);
		
	}

	@Override
	@Transactional
	public void saveRefreshToken(RefreshTokenAuthDto refreshTokenAuthDto) throws Exception {
		authMapper.insertRefreshToken(refreshTokenAuthDto);
		
	}

	@Override
	@Transactional
	public void removeRefreshToken(RefreshTokenAuthDto refreshTokenAuthDto) throws Exception {
		authMapper.deleteOneRefreshToken(refreshTokenAuthDto);
		
	}
	
	
	

}
