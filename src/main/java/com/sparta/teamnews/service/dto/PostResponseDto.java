package com.sparta.teamnews.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.teamnews.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class PostResponseDto {

    // TODO: JPA -> QueryDSL join (service, repository code refactoring & adding needed)

    private Long id;
    private String title;
    private String profileName;
    private String content;
    private String savedNm;
    private String savedPath;
    private String orgNm;
    private Boolean success;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<CommentResponseDto> comment;
    private Integer like;
    private List<LikeResponseDto> likeList;

    public PostResponseDto(Post post) {
        this.id = post.getPostId();
        this.title = post.getTitle();
        this.profileName = post.getUser().getProfileName();
        this.content = post.getContent();
        this.savedNm = post.getSavedName();
        this.savedPath = post.getSavedPath();
        this.orgNm = post.getOriginalName();

        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.comment = post.getCommentList()
                .stream()
                .map(CommentResponseDto::new)
                .toList();
        this.like = post.getLikeList().size();
        this.likeList = post.getLikeList()
                .stream()
                .map(LikeResponseDto::new)
                .toList();
    }

    public PostResponseDto(Boolean success) {
        this.success = success;
    }
}
