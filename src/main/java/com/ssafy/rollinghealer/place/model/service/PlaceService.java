package com.ssafy.rollinghealer.place.model.service;

import java.util.List;

import com.ssafy.rollinghealer.place.model.AreaBasedList1ApiQueryParams;
import com.ssafy.rollinghealer.place.model.PlaceInfoDto;

public interface PlaceService {
	void fetchPlaceInfoDataByAreaCode(AreaBasedList1ApiQueryParams query) throws Exception;
}
