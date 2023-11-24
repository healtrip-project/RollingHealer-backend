package com.ssafy.rollinghealer.plan.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.rollinghealer.plan.model.PlanDto;
import com.ssafy.rollinghealer.plan.model.PlanItemDto;
import com.ssafy.rollinghealer.plan.model.PlanSearchDto;
import com.ssafy.rollinghealer.plan.model.service.PlanService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/plan")
@RequiredArgsConstructor
public class PlanController {
	private final PlanService planService;

	@GetMapping("/{planId}")
	public PlanDto getPlan(@PathVariable int planId) {
	 return planService.getPlan(planId);
	}
	
	@PostMapping
	public void savePlan(@RequestBody PlanDto planDto) {
	 planService.savePlan(planDto);
	}
	
	@PutMapping("/{planId}")
	public void updatePlan(@PathVariable int planId, @RequestBody PlanDto planDto) {
	 planDto.setPlanId(planId);
	 planService.updatePlan(planDto);
	}
	
	@DeleteMapping("/{planId}")
	public void deletePlan(@PathVariable int planId) {
	 planService.deletePlan(PlanDto.builder().planId(planId).build());
	}

 

}