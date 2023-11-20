package com.ssafy.rollinghealer.file.model.service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.rollinghealer.file.model.FileInfoDto;
import com.ssafy.rollinghealer.file.model.FileInfoResourceDto;
import com.ssafy.rollinghealer.file.model.FileSaveResponseDto;
import com.ssafy.rollinghealer.file.model.FileSearchDto;

public interface FileService {

	List<FileSaveResponseDto> saveFileInfo(MultipartFile[] files) throws Exception;
	List<FileInfoDto> FileInfoList(FileSearchDto fileSearchDto);
//	Resource downloadFile(FileInfoDto fileInfoDto) throws Exception;
	FileInfoResourceDto downloadFile(String fileName) throws Exception;
	Resource downloadImage(String fileName) throws Exception;
	FileInfoDto getFileInfo(String fileName);
}
