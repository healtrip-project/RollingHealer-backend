package com.ssafy.rollinghealer.post.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.rollinghealer.post.model.PostDto;
import com.ssafy.rollinghealer.post.model.mapper.PostMapper;

@Service
public class PostServiceImpl implements PostService {
	private PostMapper postMapper;
	public PostServiceImpl(PostMapper postMapper) {
		this.postMapper = postMapper;
	}

    @Override
    public void createPost(PostDto postDto) {
        postMapper.createPost(postDto);
    }

    @Override
    public List<PostDto> getAllPosts() {
        return postMapper.getAllPosts();
    }

    @Override
    public PostDto getPostById(int postId) {
        return postMapper.getPostById(postId);
    }

    @Override
    public void updatePost(PostDto postDto) {
        postMapper.updatePost(postDto);
    }

    @Override
    public void deletePost(int postId) {
        postMapper.deletePost(postId);
    }
	
	
}
