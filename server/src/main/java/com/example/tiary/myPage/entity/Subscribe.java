package com.example.tiary.myPage.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
@Entity
@IdClass(Subscribe.class)
public class Subscribe {
    @Id
    private Long userId;
    @Id
    private Long writerId;
}