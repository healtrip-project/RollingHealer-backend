package com.ssafy.rollinghealer.plan.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.rollinghealer.plan.model.PlanDto;
import com.ssafy.rollinghealer.plan.model.PlanItemDto;
import com.ssafy.rollinghealer.plan.model.PlanSearchDto;

@Mapper
public interface PlanMapper {
	PlanDto selectOnePlan(int planId);
	PlanItemDto selectOnePlanItem(int planItemIdx);
	List<PlanDto> selectPlanList(PlanSearchDto planSearchDto);
	void insertPlan(PlanDto planDto);
	void insertPlanItemList(PlanDto planDto);
	void updatePlan(PlanDto planDto);
	void deletePlan(PlanDto planDto);
	void deletePlanItem(PlanItemDto planItemDto);
}
