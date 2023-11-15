package com.ssafy.rollinghealer.post.model;

import lombok.Data;

@Data
public class PostDto {
	private int postId;
	private String title;
	private String content;
	private String createBy;
	private String createAt;
	private boolean isDelete;
	private boolean isUseFile;
	private boolean isPlan;
	private String updateAt;
	private String deleteAt;
	private String postType;
}
