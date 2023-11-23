package com.ssafy.rollinghealer.member.model.service;

import com.ssafy.rollinghealer.member.model.UserDto;

public interface MemberService {
	UserDto userInfo(String userId) throws Exception;

	void updateUserThumbnail(UserDto userDto);
}
