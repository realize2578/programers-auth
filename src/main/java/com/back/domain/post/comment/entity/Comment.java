package com.back.domain.post.comment.entity;

import com.back.domain.member.entity.Member;
import com.back.domain.post.post.entity.Post;
import com.back.global.entity.BaseEntity;
import com.back.global.exception.ServiceException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Comment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    Member author;

    private String content;

    @ManyToOne
    @JsonIgnore
    private Post post;

    public Comment(Member author, String content, Post post) {
        this.author = author;
        this.content = content;
        this.post = post;
    }

    public void checkActorModify(Member actor){
        if(!this.author.getId().equals(actor.getId())) {
            throw new ServiceException("403-1","권한이 없습니다.");// 403 인가실패
        }
    }

    public void checkActorDelete(Member actor) {
        if(!this.author.getId().equals(actor.getId())) {
            throw new ServiceException("403-2", "삭제 권한이 없습니다.");
        }
    }


    public void update(String content) {
        this.content = content;
    }
}