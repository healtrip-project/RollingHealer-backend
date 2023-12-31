package com.ssafy.rollinghealer.plan.model;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
@Getter
@ToString
@Builder
public class PlanSearchDto {
	private int planId;
	private String planTitle;
	private String startDate;
	private String createBy;
	private int isDelete;
	private int isPublic;
}
