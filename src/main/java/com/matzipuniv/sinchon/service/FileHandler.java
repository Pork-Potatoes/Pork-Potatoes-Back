package com.matzipuniv.sinchon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class FileHandler{
    public String parseFileInfo(MultipartFile multipartFile) throws Exception{
        String file_path;

        if(multipartFile.isEmpty()){
            return null;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String current_date = simpleDateFormat.format(new Date());
        String file_name = current_date+ "_" + multipartFile.getOriginalFilename();

        String absolutePath = new File("").getAbsolutePath()+"/src/main/resources/static";
        String path = "/uploads";

        String contentType = multipartFile.getContentType();
        String originalFileExtension;

        if(ObjectUtils.isEmpty(contentType)){
            return null;
        }else{
            if(contentType.contains("image/jpeg")){
                originalFileExtension = "_jpg";
            }
            else if(contentType.contains("image/png")){
                originalFileExtension = "_png";
            }
            else{
                return null;
            }
        }

        File file = new File(absolutePath+path);
        if(!file.exists()){
            file.mkdirs();
        }

        file_path = path+"/"+file_name;
        file = new File(absolutePath+file_path);
        multipartFile.transferTo(file);

        return file_path;
    }
}