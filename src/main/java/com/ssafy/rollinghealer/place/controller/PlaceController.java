package com.ssafy.rollinghealer.place.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.rollinghealer.place.model.AreaBasedList1ApiQueryParams;
import com.ssafy.rollinghealer.place.model.PlaceInfoDto;
import com.ssafy.rollinghealer.place.model.PlaceInfoExtraDto;
import com.ssafy.rollinghealer.place.model.PlaceSearchDto;
import com.ssafy.rollinghealer.place.model.service.PlaceService;
import com.ssafy.rollinghealer.util.SizeConstant;

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
	
	@PutMapping("/update")
	public ResponseEntity<?> updatePlaceInfo(@RequestBody PlaceInfoDto placeInfoDto) {
       placeService.updatePlaceInfo(placeInfoDto);
       return ResponseEntity.ok().build();
   }
	
	@DeleteMapping("/{contentId}")
	public ResponseEntity<?> changePlaceInfoDeleteStatus(@PathVariable String contentId) {
	    placeService.changePlaceInfoDeleteStatus(PlaceInfoExtraDto.builder()
	    		.content_id(contentId)
	    		.is_delete(1)
	    		.build());
	    return ResponseEntity.ok().build();
	}
		
	@DeleteMapping("/admin/oldDataClear")
	public ResponseEntity<Void> deletePlaceInfoOneMonthfromDeleteAt() {
	    placeService.deletePlaceInfoOneMonthfromDeleteAt();
	    return ResponseEntity.ok().build();
	}
	
	@PostMapping("/regist")
	public ResponseEntity<?> registCustomPlaceInfo(@RequestBody PlaceInfoDto placeInfoDto) {
		System.out.println(placeInfoDto);
	    placeService.registCustomPlaceInfo(placeInfoDto);
	    return ResponseEntity.ok().build();
	}
		
	@GetMapping("/search")
	public ResponseEntity<List<PlaceInfoDto>> searchPlaceInfoList(@ModelAttribute PlaceSearchDto placeSearchDto) {
		placeSearchDto.setSize(SizeConstant.LIST_SIZE*10);
		placeSearchDto.setStart(0);
		List<PlaceInfoDto> placeInfoDtoList = placeService.searchPlaceInfoList(placeSearchDto);
		return ResponseEntity.ok(placeInfoDtoList);
	}
	@GetMapping("/{contentId}")
	public ResponseEntity<PlaceInfoDto> getPlaceInfo(@PathVariable String contentId) {
	   PlaceInfoDto placeInfoDto = placeService.getPlaceInfo(contentId);
	   return ResponseEntity.ok(placeInfoDto);
	}
}
