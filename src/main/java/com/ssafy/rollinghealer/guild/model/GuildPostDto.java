package com.ssafy.rollinghealer.guild.model;

import lombok.Data;

@Data
public class GuildPostDto {
	private int postId;
    private String title;
    private String content;
    private String createdBy;
    private String createdAt;
    private boolean isDelete;
    private boolean isUseFile;
    private String deleteAt;
    private Integer guildId; // Integer를 사용하여 nullable 필드를 나타냄
    private String guildBoardType;
    private String deleteBy;

}
