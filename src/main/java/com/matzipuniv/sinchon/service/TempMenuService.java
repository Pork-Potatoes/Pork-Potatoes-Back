package com.matzipuniv.sinchon.service;

import com.matzipuniv.sinchon.domain.TempMenu;
import com.matzipuniv.sinchon.domain.TempMenuRepository;
import com.matzipuniv.sinchon.web.dto.TempMenuDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TempMenuService {

    private final TempMenuRepository tempMenuRepository;

    public TempMenuService(TempMenuRepository tempMenuRepository){
        this.tempMenuRepository = tempMenuRepository;
    }

    public List<TempMenu> getAllTempMenu(){
        return tempMenuRepository.findAll();
    }

    public void registerTempMenu(TempMenuDto tempMenuDto){
        TempMenu tempMenu = new TempMenu();
        tempMenu.setRestaurant(tempMenuDto.getRestaurant());
        tempMenu.setTempMenuName(tempMenuDto.getTempMenuName());

        tempMenuRepository.save(tempMenu);
    }
}
