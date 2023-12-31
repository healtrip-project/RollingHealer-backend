package com.ssafy.rollinghealer.guild.model.service;

import java.util.List;

import com.ssafy.rollinghealer.guild.model.GuildDto;
import com.ssafy.rollinghealer.guild.model.GuildPostDto;
import com.ssafy.rollinghealer.member.model.UserDto;


public interface GuildService {

	void makeGuild(GuildDto guildDto);

	List<GuildDto> guildList();

	void modifyGuild(GuildDto guildDto);

	GuildDto detailGuild(String guildName);
	
	void deleteGuild(GuildDto guildDto);

	void guildPostWrite(GuildPostDto guildPostDto);

	List<GuildPostDto> guildPostList(int guildId);

	GuildPostDto detailGuildPost(int postId);

	void modifyGuildPost(GuildPostDto guildPostDto);

	void deleteGuildPost(GuildPostDto guildPostDto);

	boolean isGuildAliasAvailable(String guildAlias);

	void joinGuild(int guildId, String userId);


	List<UserDto> guildMemberList(int guildId);

	void updateGuildThumbnail(GuildDto guildDto);


}
