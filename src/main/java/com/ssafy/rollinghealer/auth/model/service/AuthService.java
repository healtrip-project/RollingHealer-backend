package com.ssafy.rollinghealer.auth.model.service;

import com.ssafy.rollinghealer.auth.model.RefreshTokenAuthDto;

public interface AuthService {

	int findRefreshTokenByRefreshToken(RefreshTokenAuthDto refreshTokenAuthDto) throws Exception;
}
