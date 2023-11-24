package com.ssafy.rollinghealer.file.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.rollinghealer.file.model.FileInfoDto;
import com.ssafy.rollinghealer.file.model.FileSearchDto;

@Mapper
public interface FileMapper {
	FileInfoDto selectOneFileInfo(String fileName);
	List<FileInfoDto> selectFileInfoList(FileSearchDto fileSearchDto);
	void insertFileInfo(FileInfoDto fileInfoDto);
}
