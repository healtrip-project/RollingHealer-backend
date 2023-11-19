package com.ssafy.rollinghealer.place.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class PlaceInfoExtraDto {
	  private String content_id;
	  private String description;
	  private String create_by;
	  private int is_delete;
	  private String delete_at;
	  private String create_at;

}