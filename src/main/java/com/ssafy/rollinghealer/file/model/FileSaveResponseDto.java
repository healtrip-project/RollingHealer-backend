package com.ssafy.rollinghealer.file.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class FileSaveResponseDto {
	private String fileOriginName;
	private String fileImage;
	private String fileDownload;
}
