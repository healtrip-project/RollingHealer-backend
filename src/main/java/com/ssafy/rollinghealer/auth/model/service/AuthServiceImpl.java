package com.ssafy.rollinghealer.auth.model.service;

import org.springframework.stereotype.Service;

import com.ssafy.rollinghealer.auth.model.RefreshTokenAuthDto;
import com.ssafy.rollinghealer.auth.model.mapper.AuthMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
	private final AuthMapper authMapper;
	
	public AuthServiceImpl(AuthMapper authMapper) {
		super();
		this.authMapper = authMapper;
	}

	@Override
	public int findRefreshTokenByRefreshToken(RefreshTokenAuthDto refreshTokenAuthDto) throws Exception {
		// TODO Auto-generated method stub
		return authMapper.selectOneRefreshToekn(refreshTokenAuthDto);
	}
	

}
