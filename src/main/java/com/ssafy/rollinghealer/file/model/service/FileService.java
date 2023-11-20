package com.ssafy.rollinghealer.file.model.service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.rollinghealer.file.model.FileInfoDto;
import com.ssafy.rollinghealer.file.model.FileSearchDto;

public interface FileService {

	List<String> saveFileInfo(MultipartFile[] files) throws Exception;
	FileInfoDto getFileInfo(int fileIdx);
	List<FileInfoDto> FileInfoList(FileSearchDto fileSearchDto);
	Resource downloadFile(FileInfoDto fileInfoDto) throws Exception;
}
