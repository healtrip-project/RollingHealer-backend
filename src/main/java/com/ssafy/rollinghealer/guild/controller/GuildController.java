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
	
	@GetMapping("{guildName}")
	public ResponseEntity<GuildDto> detailGuild(@PathVariable String guildName) {
		return ResponseEntity.ok(guildService.detailGuild(guildName));
	}
	
	@PutMapping("{guildName}")
	public ResponseEntity<String> modifyGuild(@PathVariable String guildName, @RequestBody GuildDto guildDto) {
		guildDto.setGuildName(guildName);
		guildService.modifyGuild(guildDto);
		return ResponseEntity.ok("OK");
	}
	
	
	///////////////		"guildpost" - 해당 길드 게시판 리스트 , 게시물 작성, 상세, 수정, 삭제 	 ///////////////////////
	@PostMapping("{guildName}/guildpost")
	public ResponseEntity<String> guildPostWrite(@PathVariable String guildName, @RequestBody GuildPostDto guildPostDto, HttpSession session) {
		GuildDto guildDto = guildService.detailGuild(guildName);
		guildPostDto.setGuildId(guildDto.getGuildId());
		//session.getAttribute("username", "")
		guildService.guildPostWrite(guildPostDto);
		return ResponseEntity.status(HttpStatus.CREATED).body("ok");
	}
	
	@GetMapping("{guildName}/guildpost")
	public ResponseEntity<List<GuildPostDto>> guildPostList(@PathVariable String guildName) {
		GuildDto guildDto = guildService.detailGuild(guildName);
		return ResponseEntity.ok().body(guildService.guildPostList(guildDto.getGuildId()));
	}
	
	@GetMapping("{guildName}/guildpost/{postId}")
	public ResponseEntity<GuildPostDto> detailGuildPost(@PathVariable String guildName, @PathVariable int postId) {
		return ResponseEntity.ok(guildService.detailGuildPost( postId));
	}
	
	@PutMapping("{guildName}/guildpost/{postId}")
	public ResponseEntity<String> modifyGuildPost(@PathVariable String guildName, @RequestBody GuildPostDto guildPostDto, @PathVariable int postId) {
//		System.out.println(" 길트포스트 수정 접근 ");
		guildPostDto.setPostId(postId);
		guildService.modifyGuildPost(guildPostDto);
		return ResponseEntity.ok("OK");
	}
	
	@PutMapping("{guildName}/guildpost/{postId}/delete")
	public ResponseEntity<String> deleteGuildPost(@PathVariable String guildName, @RequestBody GuildPostDto guildPostDto, @PathVariable int postId) {
		guildPostDto.setPostId(postId);
//		guildPostDto.setDeleteBy("임시 삭제 누가했는지 아이디");
		guildService.deleteGuildPost(guildPostDto);
		return ResponseEntity.ok("OK");
	}
	
	
}
