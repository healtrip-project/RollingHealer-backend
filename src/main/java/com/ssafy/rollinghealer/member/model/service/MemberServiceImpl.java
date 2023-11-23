package com.ssafy.rollinghealer.member.model.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ssafy.rollinghealer.member.model.UserDto;
import com.ssafy.rollinghealer.member.model.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberServiceImpl implements MemberService {
	
	private final MemberMapper memberMapper;

	@Override
	public UserDto userInfo(String userId) throws Exception {
		UserDto user=null;
		if(SecurityContextHolder.getContext().getAuthentication().getName().equals(userId)) {
			user=memberMapper.selectOneUser(userId);
		}
		if(user==null) {
			log.error("유저 정보없음");
			throw new Exception("유저 정보없음");
		}
		return user;
	}
	@Override
	public void updateUserThumbnail(UserDto userDto) {
	      memberMapper.updateUserThumbnail(userDto);
  }
}
