package com.matzipuniv.sinchon.web.dto;

import com.matzipuniv.sinchon.domain.Folder;
import com.matzipuniv.sinchon.domain.User;
import com.matzipuniv.sinchon.domain.UserRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
public class FolderSaveRequestDto {
    private String title;
    private User user;

    @Builder
    public FolderSaveRequestDto(String title, User user){
        this.title = title;
        this.user = user;
    }

    public Folder toEntity(){
        return Folder.builder()
                .title(title)
                .pinnedCnt(0)
                .user(user)
                .deleteFlag(false)
                .build();
    }
}
