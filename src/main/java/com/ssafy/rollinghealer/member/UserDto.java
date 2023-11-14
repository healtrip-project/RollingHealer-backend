package com.ssafy.rollinghealer.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDto {
    private int idx;
    private String userId;
    private String userPassword;
    private String userName;
    private String userNickname;
    private Grade grade;
    private long userExp;
    private String userThumbnailFileUrl;
    private int isDelete;
    private String updatedAt; // Changed to String
    private String createdAt; // Changed to String
    private String userEmailId;
    private String userEmailDomain;
    private String description;
    private int guildId;


    public enum Grade {
        USER,
        ADMIN
    }

}