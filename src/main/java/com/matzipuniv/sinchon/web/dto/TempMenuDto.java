package com.matzipuniv.sinchon.web.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.matzipuniv.sinchon.domain.TempMenu;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.beans.ConstructorProperties;

@Getter
@Setter
@ToString
public class TempMenuDto {

    private Long tempMenuNum;
    private Long restaurant;
    private String tempMenuName;

    public static TempMenuDto of(TempMenu tempMenu){
        TempMenuDto tempMenuDto = new TempMenuDto();
        tempMenuDto.setTempMenuNum(tempMenu.getTempMenuNum());
        tempMenuDto.setRestaurant(tempMenu.getRestaurant());
        tempMenuDto.setTempMenuName(tempMenuDto.getTempMenuName());

        return tempMenuDto;
    }
}
