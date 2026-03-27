package com.back.domain.post.comment.entity;

import com.back.domain.post.post.entity.Post;
import com.back.global.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Comment extends BaseEntity {
    private String content;

    @ManyToOne
    @JsonIgnore
    private Post post;

    public void update(String content) {
        this.content = content;
    }
}