package com.ssafy.rollinghealer.post.model.service;

import java.util.List;

import com.ssafy.rollinghealer.post.model.PostDto;

public interface PostService {

    void createPost(PostDto postDto);

    List<PostDto> getAllPosts();

    PostDto getPostById(int postId);

    void updatePost(PostDto postDto);

    void deletePost(int postId);

}
