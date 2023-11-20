package com.ssafy.rollinghealer.file.controller;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.rollinghealer.file.model.FileInfoDto;
import com.ssafy.rollinghealer.file.model.service.FileService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/file")
public class FileController {
	private final FileService fileService;

	@PostMapping("/upload")
	public ResponseEntity<List<String>> uploadFile(@RequestParam("file") MultipartFile[] files) {
		// 파일 정보 생성
		// fileInfo 필드 설정
		// ...

		List<String> list = null;
		try {
			list = fileService.saveFileInfo(files);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 파일 정보 저장

		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/download/{fileIdx}")
	public ResponseEntity<?> downloadFile(@PathVariable int fileIdx) throws Exception {
		FileInfoDto fileInfoDto = fileService.getFileInfo(fileIdx);
		Resource file = fileService.downloadFile(fileInfoDto);
		return ResponseEntity.badRequest().build();
//		return ResponseEntity.ok()
//		        .contentType(MediaType.parseMediaType(fileInfoDto.getFileType()))
//		        .charset(StandardCharsets.UTF_8)
//		        .body(file);
		
	}
	
}
