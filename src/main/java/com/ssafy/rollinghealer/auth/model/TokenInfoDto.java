package com.ssafy.rollinghealer.auth.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class TokenInfoDto {
	private String refreshToken;
	private String accessToken;
	@Builder
	public TokenInfoDto(String refreshToken, String accessToken) {
		super();
		this.refreshToken = refreshToken;
		this.accessToken = accessToken;
	}
	
}
