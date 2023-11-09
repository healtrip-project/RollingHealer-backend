package com.ssafy.rollinghealer.auth;
/**
 * @since 2023
 * @author <mudokja@gmail.com>
 *
 */

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

import org.springframework.stereotype.Component;

import com.google.common.hash.Hashing;

@Component
public class ShaUtil {
	private final static int gibonStretch=5;
	private final static String gibonSalt="aaawaegweggedszzsvd";
	private static final SecureRandom random = new SecureRandom();
	private ShaUtil() {
		
	}
	
	
	/**
	 * @see 미완상태(적용방식 미확정)
	 * @return
	 */
	private String generateSalt() {
		byte[] salt = new byte[32];
		random.nextBytes(salt);
		return null;
		
	}
	public static String sha256encode(String code) {
		return sha256encode(code,gibonSalt,gibonStretch);
		
	}
	public static String sha256encode(String code, String salt, int stretchingNum) {
		if(stretchingNum==0) {
			return Hashing.sha256()
					  .hashString(code+salt, StandardCharsets.UTF_8)
					  .toString();
		}
		return  Hashing.sha256()
				  .hashString(sha256encode(code, salt, stretchingNum-1)+salt, StandardCharsets.UTF_8)
				  .toString();
		
	}
	
}
