package com.ssafy.rollinghealer.guild.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.rollinghealer.guild.model.GuildDto;
import com.ssafy.rollinghealer.guild.model.GuildPostDto;

@Mapper
public interface GuildMapper {

	void insertGuild(GuildDto guildDto);

	List<GuildDto> selectGuild();

	void updateGuild(GuildDto guildDto);

	GuildDto selectByOneGuild(String guildAlias);
	
	void deleteGuild(GuildDto guildDto);

	void insertGuildPost(GuildPostDto guildPostDto);

	List<GuildPostDto> selectGuildPost(int guildId);

	GuildPostDto selectByOneGuildPost(int postId);

	void updateGuildPost(GuildPostDto guildPostDto);

	void deleteGuildPost(GuildPostDto guildPostDto);


}
