package com.ssafy.rollinghealer.place.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.rollinghealer.place.model.PlaceInfoDto;
import com.ssafy.rollinghealer.place.model.PlaceInfoExtraDto;
import com.ssafy.rollinghealer.place.model.PlaceSearchDto;
@Mapper
public interface PlaceMapper {

    void insertPlaceInfoDtoList(List<PlaceInfoDto> placeInfoDtoList);
    PlaceInfoDto selectOnePlaceInfo(String contentId);
    List<PlaceInfoDto> selectPlaceInfoList(PlaceSearchDto placeSearchDto);
    void updatePlaceInfo(PlaceInfoDto placeInfoDto);
    void updateDeleteStatus(PlaceInfoExtraDto placeInfoExtraDto);
    void deletePlaceInfoExtraOneMonthfromDeleteAt();
    void deletePlaceInfoOneMonthfromDeleteAt();
    void insertCustomPlaceInfo(PlaceInfoDto placeInfoDto);
    void insertCustomPlaceInfoExtra(PlaceInfoExtraDto placeInfoExtraDto);
    
    
}