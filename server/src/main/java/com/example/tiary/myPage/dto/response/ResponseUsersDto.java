package com.example.tiary.myPage.dto.response;

import com.example.tiary.users.constant.Role;

import com.example.tiary.users.constant.UserStatus;
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
    private Long id;
    private String nickname;
    @Email
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String userPicture;
    private UserStatus userStatus;
    public static ResponseUsersDto from(Users users) {
        return ResponseUsersDto.builder()
                .id(users.getId())
                .nickname(users.getNickname())
                .email(users.getEmail())
                .role(users.getRole())
                .userPicture(users.getUserPicture())
                .userStatus(users.getUserStatus())
                .build();
    }
}