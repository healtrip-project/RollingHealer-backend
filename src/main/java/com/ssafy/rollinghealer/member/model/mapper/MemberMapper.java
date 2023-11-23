package com.ssafy.rollinghealer.member.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.rollinghealer.member.model.UserDto;


@Mapper
public interface MemberMapper {
	UserDto selectOneUser(String userId);

	void updateUserThumbnail(UserDto userDto);

	List<UserDto> selectUserList();
}
