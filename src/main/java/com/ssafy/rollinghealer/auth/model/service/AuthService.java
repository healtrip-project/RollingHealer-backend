package com.ssafy.rollinghealer.auth.model.service;

import com.ssafy.rollinghealer.auth.model.RefreshTokenAuthDto;
import com.ssafy.rollinghealer.member.model.UserDto;

public interface AuthService {

	int findRefreshTokenByRefreshToken(RefreshTokenAuthDto refreshTokenAuthDto) throws Exception;
	void userJoin(UserDto userDto) throws Exception;
	void saveRefreshToken(RefreshTokenAuthDto refreshTokenAuthDto) throws Exception;
	void removeRefreshToken(RefreshTokenAuthDto refreshTokenAuthDto) throws Exception;
}
