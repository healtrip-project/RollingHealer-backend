package com.ssafy.rollinghealer.plan.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.rollinghealer.plan.model.PlanDto;
import com.ssafy.rollinghealer.plan.model.PlanItemDto;
import com.ssafy.rollinghealer.plan.model.PlanSearchDto;
import com.ssafy.rollinghealer.plan.model.mapper.PlanMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {
	 private final PlanMapper planMapper;

	@Override
	public PlanDto getPlan(int planId) {
	    return planMapper.selectOnePlan(planId);
	}
	@Override
	public PlanItemDto getPlanItems(int planItemIdx) {
	    return planMapper.selectOnePlanItem(planItemIdx);
	}
	@Override
	public List<PlanDto> searchPlanList(PlanSearchDto planSearchDto){
	  return planMapper.selectPlanList(planSearchDto);
	}
	@Override
	public void updatePlan(PlanDto planDto) {
	    planMapper.updatePlan(planDto);
	}
	@Override
	@Transactional
	public void savePlan(PlanDto planDto) {
	    planMapper.insertPlan(planDto);
	    planMapper.insertPlanItemList(planDto);
	}
	@Override
	public void deletePlan(PlanDto planDto) {
	     planMapper.deletePlan(planDto);
	}
	@Override
	public void deletePlanItem(PlanItemDto planItemDto) {
	    planMapper.deletePlanItem(planItemDto);
	}
}
