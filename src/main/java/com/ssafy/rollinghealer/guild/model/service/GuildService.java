package com.ssafy.rollinghealer.guild.model.service;

import java.util.List;

import com.ssafy.rollinghealer.guild.model.GuildDto;
import com.ssafy.rollinghealer.guild.model.GuildPostDto;


public interface GuildService {

	void makeGuild(GuildDto guildDto);

	List<GuildDto> guildList();

	void modifyGuild(GuildDto guildDto);

	GuildDto detailGuild(String guildName);

	void guildPostWrite(GuildPostDto guildPostDto);

	List<GuildPostDto> guildPostList(int guildId);

	GuildPostDto detailGuildPost(int postId);

	void modifyGuildPost(GuildPostDto guildPostDto);

	void deleteGuildPost(GuildPostDto guildPostDto);


}
