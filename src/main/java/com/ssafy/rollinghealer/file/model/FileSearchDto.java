package com.ssafy.rollinghealer.file.model;

import lombok.Builder;
import lombok.ToString;
import lombok.Getter;

@Builder
@Getter
@ToString
public class FileSearchDto {
   private String uploadBy;
   private Integer isImg;
   private Integer isDelete;
   private String fileOriginName;

}
