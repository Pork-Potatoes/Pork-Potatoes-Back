package com.matzipuniv.sinchon.web.dto;

import com.matzipuniv.sinchon.domain.Addition;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
public class AdditionResponseDto {
    private Long folderNum;
    private List<RestaurantListResponseDto> restaurants;
}
