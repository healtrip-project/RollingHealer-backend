package com.ssafy.rollinghealer.auth.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.rollinghealer.auth.model.RefreshTokenAuthDto;
import com.ssafy.rollinghealer.auth.model.UserAuthDto;

@Mapper
public interface AuthMapper {
	UserAuthDto selectOneAuthenticationByUserName(String userId);
	int selectOneRefreshToekn(RefreshTokenAuthDto refreshTokenAuthDto);
}
