package com.matzipuniv.sinchon.web;

import com.matzipuniv.sinchon.domain.TempMenu;
import com.matzipuniv.sinchon.service.TempMenuService;
import com.matzipuniv.sinchon.web.dto.TempMenuDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TempMenuController {

    private final TempMenuService tempMenuService;

    public TempMenuController(TempMenuService tempMenuService){
        this.tempMenuService = tempMenuService;
    }

    @GetMapping("api/temp-menus")
    public List<TempMenu> getAllTempMenu(){
        return tempMenuService.getAllTempMenu();
    }

    @PostMapping("/api/temp-menus")
    public String registerTempMenu(@RequestBody TempMenuDto tempMenuDto){
        tempMenuService.registerTempMenu(tempMenuDto);

        return "Success";
    }
}
