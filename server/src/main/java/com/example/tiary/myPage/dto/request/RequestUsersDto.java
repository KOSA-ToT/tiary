package com.example.tiary.myPage.dto.request;

import com.example.tiary.users.constant.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class RequestUsersDto {
    private String nickname;
    @Email
    private String email;
    private String userPicture;
}
