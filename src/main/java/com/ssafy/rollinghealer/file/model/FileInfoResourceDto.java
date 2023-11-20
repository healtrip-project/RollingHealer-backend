package com.ssafy.rollinghealer.file.model;

import org.springframework.core.io.Resource;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
@Getter
@Builder
@ToString
public class FileInfoResourceDto {
   private FileInfoDto fileInfo;
   private Resource resource;
   
   
}
