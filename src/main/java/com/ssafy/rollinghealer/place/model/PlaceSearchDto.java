package com.ssafy.rollinghealer.place.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class PlaceSearchDto {
	private String type;
	private int contentId;
	private int contentTypeId;
	private String title;
	private String addr1;
	private String addr2;
	private int sidoCode;
}
