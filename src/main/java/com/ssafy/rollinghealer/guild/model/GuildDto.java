package com.ssafy.rollinghealer.guild.model;

import lombok.Data;

@Data
public class GuildDto {
	private int guildId;			// has DEFAULT
    private String guildAlias;
    private String guildName;
    private String description;
    private String goal;			
    private String createdAt;		// has DEFAULT
    private String createdBy;		
    private boolean isDelete;		// has DEFAULT
    private String deleteAt;		// has DEFAULT
    private int userCount;			// has DEFAULT
    private String guildManager;	// has DEFAULT
    private String deleteBy;		// has DEFAULT
}
