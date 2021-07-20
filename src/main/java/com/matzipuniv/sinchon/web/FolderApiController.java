package com.matzipuniv.sinchon.web;

import com.matzipuniv.sinchon.service.FolderService;
import com.matzipuniv.sinchon.web.dto.AdditionResponseDto;
import com.matzipuniv.sinchon.web.dto.FolderResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class FolderApiController {
    private final FolderService folderService;

    @GetMapping("/api/folders/{folderNum}")
    public FolderResponseDto findByNum(@PathVariable Long folderNum){
        return folderService.findById(folderNum);
    }

    @GetMapping("/api/folders/{folderNum}/restaurants")
    public AdditionResponseDto getRestaurants(@PathVariable Long folderNum){
        return folderService.getRestaurants(folderNum);
    }

    @GetMapping("/api/folders")
    public List<FolderResponseDto> findByOrderByPinnedCnt(@RequestParam(value="sort", required = false, defaultValue = "id") String value){
        if(value.equals("-pinnedCnt"))
            return folderService.findByOrderByPinnedCnt();
        return folderService.findAll();
    }

    @GetMapping("/api/users/{userNum}/folders")
    public List<FolderResponseDto> findByUser(@PathVariable Long userNum){
        return folderService.findByUser(userNum);
    }

    @DeleteMapping("/api/users/{userNum}/folders/{folderNum}")
    public String deleteFolder(@PathVariable Long userNum, @PathVariable Long folderNum){
        return folderService.deleteFolder(userNum, folderNum);
    }

    @DeleteMapping("/api/users/{userNum}/folders/{folderNum}/restaurants/{restaurantNum}")
    public String deleteRestaurant(@PathVariable Long userNum, @PathVariable Long folderNum, @PathVariable Long restaurantNum){
        return folderService.deleteRestaurant(userNum,folderNum,restaurantNum);
    }
}
