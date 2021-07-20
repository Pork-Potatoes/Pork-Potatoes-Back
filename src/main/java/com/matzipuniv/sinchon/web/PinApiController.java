package com.matzipuniv.sinchon.web;


import com.matzipuniv.sinchon.service.PinService;
import com.matzipuniv.sinchon.web.dto.PinResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PinApiController {

    private final PinService pinService;

    @GetMapping("/api/users/{userNum}/pins")
    public List<PinResponseDto> findByUserByCreatedDate(@PathVariable Long userNum, @RequestParam(value="sort", required = false, defaultValue = "id") String value) {
        List<PinResponseDto> pinResponseDtos = pinService.findByUser(userNum);
        if(value.equals("-CreatedDate")) {
            return pinService.findByUserByCreatedDate(userNum);
        }
        return pinResponseDtos;
    }

    @PostMapping("/api/users/{userNum}/pins/{folderNum}")
    public String addPin(@PathVariable Long userNum, @PathVariable Long folderNum){
        pinService.addPin(userNum, folderNum);

        return "Success";
    }

    @DeleteMapping("/api/users/{userNum}/pins/{folderNum}")
    public String deletePin(@PathVariable Long userNum, @PathVariable Long folderNum){
        pinService.deletePin(userNum, folderNum);

        return "Success";
    }
}
