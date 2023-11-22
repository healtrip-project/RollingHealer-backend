package com.ssafy.rollinghealer.plan.model.service;

import java.util.List;

import com.ssafy.rollinghealer.plan.model.PlanDto;
import com.ssafy.rollinghealer.plan.model.PlanItemDto;
import com.ssafy.rollinghealer.plan.model.PlanSearchDto;

public interface PlanService {

	PlanDto getPlan(int planId);

	PlanItemDto getPlanItems(int planItemIdx);

	List<PlanDto> searchPlanList(PlanSearchDto planSearchDto);

	void updatePlan(PlanDto planDto);

	void savePlan(PlanDto planDto);

	void deletePlan(PlanDto planDto);

	void deletePlanItem(PlanItemDto planItemDto);

}
