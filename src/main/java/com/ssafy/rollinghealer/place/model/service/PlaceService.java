package com.ssafy.rollinghealer.place.model.service;

import java.util.List;

import com.ssafy.rollinghealer.place.model.AreaBasedList1ApiQueryParams;
import com.ssafy.rollinghealer.place.model.PlaceInfoDto;
import com.ssafy.rollinghealer.place.model.PlaceInfoExtraDto;
import com.ssafy.rollinghealer.place.model.PlaceSearchDto;

public interface PlaceService {
	void fetchPlaceInfoDataByAreaCode(AreaBasedList1ApiQueryParams query) throws Exception;
    void updatePlaceInfo(PlaceInfoDto placeInfoDto);
	void changePlaceInfoDeleteStatus(PlaceInfoExtraDto placeInfoExtraDto);
	void deletePlaceInfoOneMonthfromDeleteAt();
	void registCustomPlaceInfo(PlaceInfoDto placeInfoDto);
	List<PlaceInfoDto> searchPlaceInfoList(PlaceSearchDto placeSearchDto);
	PlaceInfoDto getPlaceInfo(String contentId);
}
