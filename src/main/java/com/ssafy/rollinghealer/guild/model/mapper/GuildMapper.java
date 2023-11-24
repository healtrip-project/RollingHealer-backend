package com.ssafy.rollinghealer.guild.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.rollinghealer.guild.model.GuildDto;
import com.ssafy.rollinghealer.guild.model.GuildPostDto;
import com.ssafy.rollinghealer.member.model.UserDto;

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

	int countGuildAlias(String guildAlias);

	void updateMemberGuild(Map<String, Object> user);
    void incrementGuildUserCount(int guildId);

	List<UserDto> guildMemberList(int guildId);
	
	void guildThumbnailImageUpdate(GuildDto guildDto);

}
