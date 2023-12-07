package com.example.tiary.users.dto;

import com.example.tiary.users.constant.Role;

import com.example.tiary.users.entity.Users;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
@Builder
public class ResponseUsersDto {
    private String nickname;
    @Email
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String userPicture;
    private String userStatus;
    public static ResponseUsersDto from(Users users) {
        return ResponseUsersDto.builder()
                .nickname(users.getNickname())
                .email(users.getEmail())
                //role?
                .userPicture(users.getUserPicture())
//                .userStatus(user.getUserStatus())
                .build();
    }
}