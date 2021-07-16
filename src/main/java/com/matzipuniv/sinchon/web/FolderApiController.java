package com.matzipuniv.sinchon.web;

import com.matzipuniv.sinchon.service.FolderService;
import com.matzipuniv.sinchon.web.dto.FolderResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/folders")
public class FolderApiController {
    private final FolderService folderService;

    @GetMapping("/{folderNum}")
    public FolderResponseDto findByNum(@PathVariable Long folderNum){
        return folderService.findById(folderNum);
    }

    @GetMapping("")
    public List<FolderResponseDto> findByOrderByPinnedCnt(@RequestParam(value="sort", required = false, defaultValue = "id") String value){
        if(value.equals("-pinnedCnt"))
            return folderService.findByOrderByPinnedCnt();
        return folderService.findAll();
    }
}
