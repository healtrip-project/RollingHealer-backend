package com.ssafy.rollinghealer.place.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.rollinghealer.place.model.PlaceInfoDto;
@Mapper
public interface PlaceMapper {

    void insertPlaceInfoDtoList(List<PlaceInfoDto> placeInfoDtoList);
    
}