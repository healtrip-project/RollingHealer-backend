package com.ssafy.rollinghealer.file.model.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sksamuel.scrimage.ImmutableImage;
import com.sksamuel.scrimage.ScaleMethod;
import com.sksamuel.scrimage.webp.WebpWriter;
import com.ssafy.rollinghealer.file.model.FileInfoDto;
import com.ssafy.rollinghealer.file.model.FileInfoResourceDto;
import com.ssafy.rollinghealer.file.model.FileSaveResponseDto;
import com.ssafy.rollinghealer.file.model.FileSearchDto;
import com.ssafy.rollinghealer.file.model.mapper.FileMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {
	@Value("${file.path.upload-images}")
	private String storageLocation;
	private static String[] PERMISSION_FILE_EXT_ARR = {"GIF", "JPEG", "JPG", "PNG", "BMP", "PDF", "MP4"};
	private final FileMapper fileMapper;
	
	public String generateUUID() {
		return UUID.randomUUID(	).toString();
	}
	
	@Transactional
	public List<FileSaveResponseDto> saveFileInfoList(List<MultipartFile> files) throws Exception {
		List<FileSaveResponseDto> FileList=new ArrayList<>();
		Path folderPath = Paths.get(storageLocation).toAbsolutePath().normalize();	
		log.debug("출력위치"+folderPath.toString());
        if((Files.exists(folderPath) && Files.isDirectory(folderPath))){
        	System.out.println("이거보셈");
            log.debug("폴더가 존재합니다.");
        }else{
        	try {
				
        		Files.createDirectories(folderPath);
        		log.debug("폴더가 생성되었습니다. :{}",folderPath);
			} catch (Exception e) {
				// TODO: handle exception
				log.debug("폴더 생성 실패 : {} ",e.getMessage());
			}
        }
        
        
		for (MultipartFile file : files) {
			String fileName = generateUUID();
			String[] fileOriginNameInfo=file.getOriginalFilename().split("\\.");
			String fileType=fileOriginNameInfo[1];
			String resizingFileName = "s_".concat(fileName);
			String filePath =folderPath.resolve(fileName).toString();
			log.debug("파일 저장 경로 :{}", filePath);
		
			try(InputStream inputStream=file.getInputStream();
				InputStream inputStream2=file.getInputStream();	
					InputStream inputStream3=file.getInputStream();	
				) {

			Files.copy(inputStream,  Paths.get(storageLocation).resolve(fileName),
					StandardCopyOption.REPLACE_EXISTING);

			BufferedImage bufferedImage = ImageIO.read(inputStream2);
		    int originWidth = bufferedImage.getWidth();
		    int originHeight = bufferedImage.getHeight();
		    int height=originHeight;
		    int width=originWidth;
		    bufferedImage.flush();
		    
		    ImmutableImage image= ImmutableImage.loader().fromStream(inputStream3);
		    double resizeRate=0.5;
		    if(height>640 || width>1280) {
		    	if(height>width) {
		    		resizeRate=(double)(height/width);
		    		height=640;
		    	}else if(width>height) {
		    		resizeRate=(double)(width/height);
		    		width=1280;
		    	}
	    	   double resizeWidth= (height/resizeRate);
	    	   width=(int)resizeWidth;
	    	}
		    image.scale(resizeRate, ScaleMethod.Bicubic);

			
				
				image.output(WebpWriter.DEFAULT, Paths.get(storageLocation).resolve(resizingFileName));
//				Thumbnails
//				.of(file.getInputStream())
//				.size(width,height)
//				.outputFormat("jpg")
//				.toFile(Paths.get(storageLocation).resolve(resizingFileName).toString());
				
				FileInfoDto fileInfo=FileInfoDto.builder()
						.fileOriginName(fileOriginNameInfo[0])
						.fileName(fileName)
						.fileSize(file.getSize())
						.uploadBy(SecurityContextHolder.getContext().getAuthentication().getName())
						.isImg(isImage(file.getOriginalFilename() != null?"":file.getOriginalFilename())?1:0)
						.fileGroupId(null)
						.fileType(fileType)
						.uploadPath(Paths.get(storageLocation).toString())
						.build();
				
				fileMapper.insertFileInfo(fileInfo);
				FileList.add(FileSaveResponseDto.builder()
						.fileOriginName(fileInfo.getFileOriginName())
						.fileImage("/file/image/"+fileInfo.getFileName())
						.fileDownload("/file/download/"+fileInfo.getFileName())
						.build());
				
			} catch (IOException e) {

				throw new Exception("Could not store file " + file.getOriginalFilename(), e);
			} 
			
		}
		return FileList;
	}
	 @Override
	 public Resource downloadImage(String fileName) throws Exception {
		 FileInfoDto fileInfoDto=fileMapper.selectOneFileInfo(fileName);
	   try {
	     Path filePath = Paths.get(fileInfoDto.getUploadPath()+File.separator+"s_"+fileInfoDto.getFileName());
	     Resource resource = new UrlResource(filePath.toUri());
	     
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
	 @Override
	 public FileInfoResourceDto downloadFile(String fileName) throws Exception {
		 FileInfoDto fileInfoDto=fileMapper.selectOneFileInfo(fileName);
		 try {
			 Path filePath = Paths.get(fileInfoDto.getUploadPath()+File.separator+fileInfoDto.getFileName());
			 Resource resource = new UrlResource(filePath.toUri());
			 
			 if (resource.exists() || resource.isReadable()) {
				 return FileInfoResourceDto.builder()
						 .fileInfo(fileInfoDto)
						 .resource(resource)
						 .build();
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
public FileInfoDto getFileInfo(String fileName) {
	return fileMapper.selectOneFileInfo(fileName);
}

@Override
public List<FileInfoDto> FileInfoList(FileSearchDto fileSearchDto) {
	return fileMapper.selectFileInfoList(fileSearchDto);
}


@Override
public FileSaveResponseDto saveFileInfo(MultipartFile file) throws Exception {
	Path folderPath = Paths.get(storageLocation).toAbsolutePath().normalize();
    if(Files.exists(folderPath) && Files.isDirectory(folderPath)){
        log.debug("폴더가 존재합니다.");
    }else{
        Files.createDirectory(folderPath);
        log.debug("폴더가 생성되었습니다. :{}",folderPath);
    }
		String fileName = generateUUID();
		Files.copy(file.getInputStream(),  Paths.get(storageLocation).resolve(fileName),
				StandardCopyOption.REPLACE_EXISTING);
		String fileType=file.getOriginalFilename().split("\\.")[1];
		String resizingFileName = "s_".concat(fileName);
		String filePath =folderPath.resolve(fileName).toString();
		log.debug("파일 저장 경로 :{}", filePath);
		BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
	    int originWidth = bufferedImage.getWidth();
	    int originHeight = bufferedImage.getHeight();
	    int height=originHeight;
	    int width=originWidth;
	    ImmutableImage image= ImmutableImage.loader().fromStream(file.getInputStream());
	    double resizeRate=0.5;
	    if(height>1280 || width>1920) {
	    	if(height>width) {
	    		resizeRate=(double)(height/width);
	    		height=1280;
	    	}else if(width>height) {
	    		resizeRate=(double)(width/height);
	    		width=1920;
	    	}
    	   double resizeWidth= (height/resizeRate);
    	   width=(int)resizeWidth;
    	}
	    image.scale(resizeRate, ScaleMethod.Bicubic);

		
		try {
			
			image.output(WebpWriter.DEFAULT, Paths.get(storageLocation).resolve(resizingFileName));
//			Thumbnails
//			.of(file.getInputStream())
//			.size(width,height)
//			.outputFormat("jpg")
//			.toFile(Paths.get(storageLocation).resolve(resizingFileName).toString());
			
			FileInfoDto fileInfo=FileInfoDto.builder()
					.fileOriginName(file.getOriginalFilename())
					.fileName(fileName)
					.fileSize(file.getSize())
					.isImg(isImage(file.getOriginalFilename() != null?"":file.getOriginalFilename())?1:0)
					.fileType(fileType)
					.uploadPath(Paths.get(storageLocation).toString())
					.build();
			
			fileMapper.insertFileInfo(fileInfo);
			return FileSaveResponseDto.builder()
					.fileOriginName(fileInfo.getFileOriginName())
					.fileImage("/image/"+fileInfo.getFileName())
					.fileDownload("/download/"+fileInfo.getFileName())
					.build();
		} catch (IOException e) {
			throw new Exception("Could not store file " + file.getOriginalFilename(), e);
		}
}

}
