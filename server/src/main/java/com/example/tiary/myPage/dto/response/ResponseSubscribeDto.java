package com.example.tiary.myPage.dto.response;

import com.example.tiary.myPage.entity.Subscribe;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class ResponseSubscribeDto {
    private Long userId;
    private Long writerId;
    private String nickname;
    private String userPicture;
    public static ResponseSubscribeDto from(Subscribe subscribe) {
        return ResponseSubscribeDto.builder()
                .userId(subscribe.getUserId())
                .writerId(subscribe.getWriterId())
                .build();
    }
}
=======
}
>>>>>>> 790e6cf ([SCRUM-27]-feat: 내 정보 수정)
