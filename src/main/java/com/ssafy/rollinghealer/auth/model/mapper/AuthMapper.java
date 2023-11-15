package com.ssafy.rollinghealer.auth.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.rollinghealer.auth.model.RefreshTokenAuthDto;
import com.ssafy.rollinghealer.auth.model.UserAuthDto;
import com.ssafy.rollinghealer.member.model.UserDto;

@Mapper
public interface AuthMapper {
	UserAuthDto selectOneAuthenticationByUserName(String userId);
	int selectOneRefreshToekn(RefreshTokenAuthDto refreshTokenAuthDto);
	void insertRefreshToken(RefreshTokenAuthDto refreshTokenAuthDto);
	void insertUser(UserDto userDto);
	void deleteOneRefreshToken(RefreshTokenAuthDto refreshTokenAuthDto);
}
