package com.ssafy.rollinghealer.place;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PlaceInfoDto {
	private String title;
	private String addr1;
	private String addr2;
	private int area_code;
	private int content_type_id;
	private double latitude;
	private double longitude;
	private String mlevel;
	private String tel;
	private String first_image;
	private String first_image2;
	private String cat1;
	private String cat2;
	private String cat3;
	private String book_tour; 
	private String update_date;
	private String create_date;
	private int content_id;
	private String create_by;
	private int is_custom;
	private int sigungucode;
}
