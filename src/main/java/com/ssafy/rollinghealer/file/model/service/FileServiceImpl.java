package com.ssafy.rollinghealer.file.model.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.rollinghealer.file.model.FileInfoDto;
import com.ssafy.rollinghealer.file.model.FileSearchDto;
import com.ssafy.rollinghealer.file.model.mapper.FileMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {
	@Value("${file.path.uplo ad-images}")
	private final String storageLocation;

	final String[] PERMISSION_FILE_EXT_ARR = {"GIF", "JPEG", "JPG", "PNG", "BMP", "PDF", "MP4"};
	private final FileMapper fileMapper;
	
	public String generateUUID() {
		return UUID.randomUUID(	).toString();
	}
	
	@Transactional
	public List<String> saveFileInfo(MultipartFile[] files) throws Exception {
		List<String> FileList=new ArrayList<String>();
		
		for (MultipartFile file : files) {
			String fileName = generateUUID() + "." + file.getOriginalFilename().split("\\.")[1];
			String resizingFileName = "s_".concat(fileName);
			String filePath =Paths.get(storageLocation).resolve(fileName).toString();
			log.debug("파일 저장 경로 :{}", filePath);
			BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
		    int originWidth = bufferedImage.getWidth();
		    int originHeight = bufferedImage.getHeight();
		    int height=originHeight;
		    int width=originWidth;
		    if(height>640) {
	    	   double resizeRate=(double)(height/width);
	    	   height=640;
	    	   int resizeWidth=(int) (height/resizeRate);
	    	   width=resizeWidth;
	    	}

			
			try {
				Files.copy(file.getInputStream(),  Paths.get(storageLocation).resolve(fileName),
						StandardCopyOption.REPLACE_EXISTING);
				
				Thumbnails
				.of(file.getInputStream())
				.size(width,height)
				.outputFormat("jpg")
				.toFile(Paths.get(storageLocation).resolve(resizingFileName).toString());
				
				FileInfoDto fileInfo=FileInfoDto.builder()
						.fileOriginName(file.getOriginalFilename())
						.fileName(fileName)
						.fileSize(file.getSize())
						.isImg(isImage(file.getOriginalFilename() != null?"":file.getOriginalFilename())?1:0)
						.fileType(file.getContentType())
						.uploadPath(Paths.get(storageLocation).toString())
						.build();
				
				fileMapper.insertFileInfo(fileInfo);
				FileList.add("/api/file/download"+fileInfo.getFileIdx());
			} catch (IOException e) {
				throw new Exception("Could not store file " + file.getOriginalFilename(), e);
			}
		}
		return null;
	}
	 @Override
	 public Resource downloadFile(FileInfoDto fileInfoDto) throws Exception {
	   String fileName = fileInfoDto.getFileName();

	   try {
	     Path filePath = Paths.get(fileInfoDto.getUploadPath());
	     Resource resource = new UrlResource(filePath.toUri()+File.separator+"s_"+fileInfoDto.getFileName());

	     if (resource.exists() || resource.isReadable()) {
	       return resource;
	     } else {
	       throw new FileNotFoundException("Could not read file: " + fileName);
	     }
	   } catch (MalformedURLException e) {
	     throw new Exception("Could not read file: " + fileName, e);
	   } catch (FileNotFoundException e) {
		
		e.printStackTrace();
	}
	return null;
	 }

public boolean isImage(String originalFilename) {
   String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
   return Arrays.asList("jpg", "png", "gif", "bmp").contains(extension.toLowerCase());
}

@Override
public FileInfoDto getFileInfo(int fileIdx) {
	return fileMapper.selectOneFileInfo(fileIdx);
}

@Override
public List<FileInfoDto> FileInfoList(FileSearchDto fileSearchDto) {
	return fileMapper.selectFileInfoList(fileSearchDto);
}

}
