package com.matzipuniv.sinchon.web.dto;

import com.matzipuniv.sinchon.domain.Folder;
import com.matzipuniv.sinchon.domain.Pin;
import com.matzipuniv.sinchon.domain.User;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
public class PinResponseDto {
    private Long pinNum;
    private Folder folder;
    private LocalDateTime createdDate;

    public PinResponseDto(Pin entity){
        this.pinNum = entity.getPinNum();
        this.folder = entity.getFolder();
        this.createdDate = entity.getCreatedDate();
    }
}
