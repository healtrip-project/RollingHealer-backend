package com.ssafy.rollinghealer.guild.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.rollinghealer.guild.model.GuildDto;
import com.ssafy.rollinghealer.guild.model.GuildPostDto;
import com.ssafy.rollinghealer.guild.model.service.GuildService;

@CrossOrigin
@RestController
@RequestMapping("/api/guild")
public class GuildController {
	private GuildService guildService;
	public GuildController(GuildService guildService) {
		this.guildService = guildService;
	}

	
	/////////////// "guild" - 길드 리스트, 생성, 수정 ///////////////////////
	@PostMapping
	public ResponseEntity<String> makeGuild(@RequestBody GuildDto guildDto) {
		guildService.makeGuild(guildDto);
		return ResponseEntity.status(HttpStatus.CREATED).body("ok");
	}
	
	@GetMapping
	public ResponseEntity<List<GuildDto>> guildList() {
		return ResponseEntity.ok().body(guildService.guildList());
	}
	
	@GetMapping("{guildAlias}")
	public ResponseEntity<GuildDto> detailGuild(@PathVariable String guildAlias) {
		return ResponseEntity.ok(guildService.detailGuild(guildAlias));
	}
	
	@PutMapping("{guildAlias}")
	public ResponseEntity<String> modifyGuild(@PathVariable String guildAlias, @RequestBody GuildDto guildDto) {
		guildDto.setGuildAlias(guildAlias);
		guildService.modifyGuild(guildDto);
		return ResponseEntity.ok("OK");
	}
	
	@PutMapping("{guildAlias}/delete")
	public ResponseEntity<String> deleteGuild(@PathVariable String guildAlias, @RequestBody GuildDto guildDto) {
		guildDto.setGuildAlias(guildAlias);
		guildService.deleteGuild(guildDto);
		return ResponseEntity.ok("OK");
	}
	
	
	
	///////////////		"guildpost" - 해당 길드 게시판 리스트 , 게시물 작성, 상세, 수정, 삭제 	 ///////////////////////
	@PostMapping("{guildAlias}/guildpost")
	public ResponseEntity<String> guildPostWrite(@PathVariable String guildAlias, @RequestBody GuildPostDto guildPostDto, HttpSession session) {
		GuildDto guildDto = guildService.detailGuild(guildAlias);
		guildPostDto.setGuildId(guildDto.getGuildId());
		//session.getAttribute("username", "")
		guildService.guildPostWrite(guildPostDto);
		return ResponseEntity.status(HttpStatus.CREATED).body("ok");
	}
	
	@GetMapping("{guildAlias}/guildpost")
	public ResponseEntity<List<GuildPostDto>> guildPostList(@PathVariable String guildAlias) {
		GuildDto guildDto = guildService.detailGuild(guildAlias);
		return ResponseEntity.ok().body(guildService.guildPostList(guildDto.getGuildId()));
	}
	
	@GetMapping("{guildAlias}/guildpost/{postId}")
	public ResponseEntity<GuildPostDto> detailGuildPost(@PathVariable String guildAlias, @PathVariable int postId) {
		return ResponseEntity.ok(guildService.detailGuildPost( postId));
	}
	
	@PutMapping("{guildAlias}/guildpost/{postId}")
	public ResponseEntity<String> modifyGuildPost(@PathVariable String guildAlias, @RequestBody GuildPostDto guildPostDto, @PathVariable int postId) {
//		System.out.println(" 길트포스트 수정 접근 ");
		guildPostDto.setPostId(postId);
		guildService.modifyGuildPost(guildPostDto);
		return ResponseEntity.ok("OK");
	}
	
	@PutMapping("{guildAlias}/guildpost/{postId}/delete")
	public ResponseEntity<String> deleteGuildPost(@PathVariable String guildAlias, @RequestBody GuildPostDto guildPostDto, @PathVariable int postId) {
		guildPostDto.setPostId(postId);
//		guildPostDto.setDeleteBy("임시 삭제 누가했는지 아이디");
		guildService.deleteGuildPost(guildPostDto);
		return ResponseEntity.ok("OK");
	}
	
	
}
