package com.ssafy.rollinghealer.post.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.rollinghealer.post.model.PostDto;

@Mapper
public interface PostMapper {

	void createPost(PostDto postDto);

    List<PostDto> getAllPosts();

    PostDto getPostById(int postId);

    void updatePost(PostDto postDto);

    void deletePost(int postId);
	
	
}
