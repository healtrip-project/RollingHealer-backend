package com.ssafy.rollinghealer.guild.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.rollinghealer.guild.model.GuildDto;
import com.ssafy.rollinghealer.guild.model.GuildPostDto;
import com.ssafy.rollinghealer.guild.model.mapper.GuildMapper;

@Service
public class GuildServiceImpl implements GuildService {

	private GuildMapper guildMapper;

	public GuildServiceImpl(GuildMapper guildMapper) {
		this.guildMapper = guildMapper;
	}

	@Override
	public void makeGuild(GuildDto guildDto) {
		guildMapper.insertGuild(guildDto);
	}

	@Override
	public List<GuildDto> guildList() {
		return guildMapper.selectGuild();
	}

	@Override
	public void modifyGuild(GuildDto guildDto) {
		guildMapper.updateGuild(guildDto);
	}

	@Override
	public GuildDto detailGuild(String guildName) {
		return guildMapper.selectByOneGuild(guildName);
	}

	@Override
	public void guildPostWrite(GuildPostDto guildPostDto) {
		guildMapper.insertGuildPost(guildPostDto);
	}

	@Override
	public List<GuildPostDto> guildPostList(int guildId) {
		return guildMapper.selectGuildPost(guildId);
	}

	@Override
	public GuildPostDto detailGuildPost(int postId) {
		return guildMapper.selectByOneGuildPost(postId);
	}

	@Override
	public void modifyGuildPost(GuildPostDto guildPostDto) {
		guildMapper.updateGuildPost(guildPostDto);
	}

	@Override
	public void deleteGuildPost(GuildPostDto guildPostDto) {
		guildMapper.deleteGuildPost(guildPostDto);
	}

	

}
