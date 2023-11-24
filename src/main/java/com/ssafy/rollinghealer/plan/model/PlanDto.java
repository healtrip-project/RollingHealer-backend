package com.ssafy.rollinghealer.plan.model;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Setter
@Builder
public class PlanDto {
   private int planId;
   private String planTitle;
   private String startDate;
   private String createBy;
   private int itemCount;
   private int isDelete;
   private String deleteAt;
   private String createAt;
   private int isPublic;
   private List<PlanItemDto> planItemList;
}