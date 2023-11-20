package com.ssafy.rollinghealer.util;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class NavigatorInfoDto {
	int startPage;
	int endPage;
	int naviSize;
	int totalPageCount;
	int prevPage;
	int nextPage;
	boolean startRange;
	boolean endRange;
	@Builder
	public NavigatorInfoDto(int startPage, int endPage, int naviSize, int totalPageCount, int prevPage, int nextPage,
			boolean startRange, boolean endRange) {
		super();
		this.startPage = startPage;
		this.endPage = endPage;
		this.naviSize = naviSize;
		this.totalPageCount = totalPageCount;
		this.prevPage = prevPage;
		this.nextPage = nextPage;
		this.startRange = startRange;
		this.endRange = endRange;
	}
	
	
}
