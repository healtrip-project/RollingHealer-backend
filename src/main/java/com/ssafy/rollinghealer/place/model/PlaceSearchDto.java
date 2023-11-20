package com.ssafy.rollinghealer.place.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@ToString
@Setter
@Builder
public class PlaceSearchDto {
	private String title;
	private Integer content_type_id;
	private Integer sidocode;
	private Integer page;
	private Integer start;
	private Integer size;
	
	
}
