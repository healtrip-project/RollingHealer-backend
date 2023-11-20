package com.ssafy.rollinghealer.place.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaceInfoDto {
	private String content_id;
	private String title;
	private String addr1;
	private String addr2;
	@JsonProperty("areacode")
	private int area_code;
	@JsonProperty("contenttypeid")
	private int content_type_id;
	@JsonProperty("mapx")
	private BigDecimal latitude;
	@JsonProperty("mapy")
	private BigDecimal longitude;
	private String mlevel;
	private String tel;
	@JsonProperty("firstimage")
	private String first_image;
	@JsonProperty("firstimage2")
	private String first_image2;
	private String cat1;
	private String cat2;
	private String cat3;
	@JsonProperty("booktour")
	private String book_tour; 
	@JsonProperty("modifiedtime")
	private String update_date;
	@JsonProperty("createdtime")
	private String create_date;
	@JsonProperty("contentid")
	private int is_custom;
	@JsonProperty("sigungucode")
	private int sigungucode;
	private PlaceInfoExtraDto extra_info;
	
//	public String getDescription() {
//		return extra_info.getDescription();
//	}
//	public String getCreate_by() {
//		return extra_info.getCreate_by();
//	}

}
