package com.matzipuniv.sinchon.web;

import com.matzipuniv.sinchon.service.FolderService;
import com.matzipuniv.sinchon.web.dto.AdditionResponseDto;
import com.matzipuniv.sinchon.web.dto.FolderResponseDto;
import com.matzipuniv.sinchon.web.dto.FolderSaveRequestDto;
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

    @PatchMapping("/api/folders/{folderNum}/title")
    public String updateTitle(@PathVariable Long folderNum, @RequestBody String title){
        return folderService.updateTitle(folderNum, title);
    }

    @PatchMapping("/api/folders/{folderNum}/description")
    public String updateDescription(@PathVariable Long folderNum, @RequestBody String description){
        return folderService.updateDescription(folderNum, description);
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

    @PostMapping("/api/restaurants/{restaurantNum}/folders/{folderNum}")
    public String saveRestaurantToFolder(@PathVariable Long restaurantNum, @PathVariable Long folderNum){
        return folderService.saveRestaurant(restaurantNum,folderNum);
    }

    @PostMapping("/api/restaurants/{restaurantNum}/folders")
    public String saveRestaurantToNewFolder(@PathVariable Long restaurantNum, @RequestBody FolderSaveRequestDto requestDto){
        return folderService.saveRestaurant(restaurantNum, folderService.saveFolder(requestDto));
    }

    @GetMapping("/api/folders/{folderNum}/restaurants")
    public AdditionResponseDto getRestaurants(@PathVariable Long folderNum){
        return folderService.getRestaurants(folderNum);
    }

    @DeleteMapping("/api/users/{userNum}/folders/{folderNum}/restaurants/{restaurantNum}")
    public String deleteRestaurant(@PathVariable Long userNum, @PathVariable Long folderNum, @PathVariable Long restaurantNum){
        return folderService.deleteRestaurant(userNum,folderNum,restaurantNum);
    }
}
