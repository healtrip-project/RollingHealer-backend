package com.ssafy.rollinghealer.auth.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class RefreshTokenAuthDto {
	private String userId;
	private String refreshToken;
	private String expirationDate;
	
}
