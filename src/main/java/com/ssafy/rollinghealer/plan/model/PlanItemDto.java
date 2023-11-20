package com.ssafy.rollinghealer.plan.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class PlanItemDto {
   private int planItemIdx;
   private String contentId;
   private int planId;
   private String memo;
   private int order;
}