package com.example.tiary.admin.entity;

import com.example.tiary.admin.constant.WriterStatus;
import com.example.tiary.users.constant.Role;
import com.example.tiary.users.constant.UserStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WriteApproval {
    @Id
    private Long userId;

    @Enumerated(EnumType.STRING)
    private WriterStatus status;

    private String nickname;

    private String email;

    private String userPicture;
    private UserStatus userStatus;
    private int articleCount;
    public void updateStatus(WriterStatus status) {
        this.status = status;
    }
}
