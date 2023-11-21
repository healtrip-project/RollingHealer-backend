package com.ssafy.rollinghealer.file.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class FileInfoDto {
	   private int fileIdx;
	   private String uploadBy;
	   private Integer fileGroupId;
	   private String fileOriginName;
	   private String fileName;
	   private String fileType;
	   private String createAt;
	   private long fileSize;
	   private int isImg;
	   private String uploadPath;
	   private int isDelete;
	   private String deleteAt;

	}
