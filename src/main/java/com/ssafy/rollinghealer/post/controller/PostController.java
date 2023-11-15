package com.ssafy.rollinghealer.post.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.ssafy.rollinghealer.post.model.PostDto;
import com.ssafy.rollinghealer.post.model.service.PostService;

@Controller
@RequestMapping("/api/post")
public class PostController {
	private PostService postService;
	public PostController(PostService postService) {
		this.postService = postService;
	}
	
	@PostMapping
    public ResponseEntity<String> createPost(@RequestBody PostDto postDto) {
        postService.createPost(postDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("ok");
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        return ResponseEntity.ok().body(postService.getAllPosts());
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable int postId) {
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<String> updatePost(@PathVariable int postId, @RequestBody PostDto postDto) {
        postDto.setPostId(postId);
        postService.updatePost(postDto);
        return ResponseEntity.ok("OK");
    }

    @PutMapping("/{postId}/delete")
    public ResponseEntity<String> deletePost(@PathVariable int postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok("OK");
    }
	
	
}
