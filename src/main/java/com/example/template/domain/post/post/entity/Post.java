package com.example.template.domain.post.post.entity;

import com.example.template.global.BaseTime;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Post extends BaseTime {

    private String title;
    private String content;

}