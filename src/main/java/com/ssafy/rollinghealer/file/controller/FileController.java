package com.ssafy.rollinghealer.file.controller;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import com.ssafy.rollinghealer.file.model.FileInfoDto;
import com.ssafy.rollinghealer.file.model.FileInfoResourceDto;
import com.ssafy.rollinghealer.file.model.FileSaveResponseDto;
import com.ssafy.rollinghealer.file.model.service.FileService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/file")
public class FileController {
	private final FileService fileService;

	@PostMapping("/upload")
	public ResponseEntity<List<FileSaveResponseDto>> uploadFile(@RequestParam("file") MultipartFile[] files) throws Exception {

		List<FileSaveResponseDto> list = null;
		list = fileService.saveFileInfo(files);

		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/image/{fileName}")
	public ResponseEntity<Resource> downloadImage(@PathVariable String fileName) {
		try {
			return ResponseEntity.ok(fileService.downloadImage(fileName));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
		
	}
	@GetMapping("/download/{fileName}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
		try {
			FileInfoResourceDto fileInfoRescourceDto=fileService.downloadFile(fileName);
			String encodedFileName = UriUtils.encode(fileInfoRescourceDto.getFileInfo().getFileOriginName(), StandardCharsets.UTF_8);
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + encodedFileName + "\"")
					.body(fileInfoRescourceDto.getResource());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
		
	}
	
}
