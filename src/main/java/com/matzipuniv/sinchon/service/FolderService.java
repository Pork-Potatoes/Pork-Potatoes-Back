package com.matzipuniv.sinchon.service;

import com.matzipuniv.sinchon.domain.Folder;
import com.matzipuniv.sinchon.domain.FolderRepository;
import com.matzipuniv.sinchon.web.dto.FolderResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FolderService {
    private final FolderRepository folderRepository;

    @Transactional(readOnly = true)
    public FolderResponseDto findById(Long num){
        Folder entity = folderRepository.findById(num)
                .orElseThrow(() -> new IllegalArgumentException("해당 폴더가 없습니다. num = "+num));

        return new FolderResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<FolderResponseDto> findAll(){
        return folderRepository.findAll().stream()
                .map(FolderResponseDto::new)
                .collect(Collectors.toList());
    }
}
