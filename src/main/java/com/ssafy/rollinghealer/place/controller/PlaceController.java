package com.ssafy.rollinghealer.place.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.rollinghealer.place.model.AreaBasedList1ApiQueryParams;
import com.ssafy.rollinghealer.place.model.service.PlaceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/place")
@RequiredArgsConstructor
public class PlaceController {
	private final PlaceService placeService;
	@GetMapping("/admin/fetchdata")
	public ResponseEntity<?> fetchData(@RequestParam(name = "numOfRows",defaultValue = "5") int num,@RequestParam(name="sidocode",defaultValue = "1") String sidoCode, @RequestParam(name="page",defaultValue="1") int page){
		
		AreaBasedList1ApiQueryParams query = AreaBasedList1ApiQueryParams.builder()
				.numOfRows(num)
				.mobileOS("ETC")
				.mobileApp("AppTest")
				.arrange("A")
				._type("json")
				.listYN("Y")
				.pageNo(page)
				.contentTypeId("")
				.areaCode(sidoCode)
				.build();
		try {
			placeService.fetchPlaceInfoDataByAreaCode(query);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
