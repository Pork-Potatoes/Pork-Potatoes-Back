package com.matzipuniv.sinchon.web.dto;

import com.matzipuniv.sinchon.domain.Folder;
import com.matzipuniv.sinchon.domain.User;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
public class FolderResponseDto {
    private Long folderNum;
    private String title;
    private String description;
    private Integer pinnedCnt;
    private User user;
    private LocalDateTime createdDate;
    private Boolean deleteFlag;

    public FolderResponseDto(Folder entity){
        this.folderNum = entity.getFolderNum();
        this.title = entity.getTitle();
        this.description = entity.getDescription();
        this.pinnedCnt = entity.getPinnedCnt();
        this.user = entity.getUser();
        this.createdDate = entity.getCreatedDate();
        this.deleteFlag = entity.getDeleteFlag();
    }
}
