package com.ssafy.rollinghealer.auth.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class LoginDto {
	private String userId;
	private String password;

}
