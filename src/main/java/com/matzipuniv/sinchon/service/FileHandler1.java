package com.matzipuniv.sinchon.service;

import com.matzipuniv.sinchon.domain.Image;
import com.matzipuniv.sinchon.web.dto.ImageResponseDto;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileHandler1 {
    private final ImageService imageService;

    public FileHandler1(ImageService imageService) {
        this.imageService = imageService;
    }

    public List<Image> parseFileInfo(List<MultipartFile> multipartFiles) throws Exception {
        List<Image> imageList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(multipartFiles)) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String currentDate = now.format(dateTimeFormatter);

            String absolutePath = new File("").getAbsolutePath() + File.separator + File.separator;

            String path = "images" + File.separator + currentDate;
            File file = new File(path);

            if (!file.exists()) {
                boolean wasSuccessful = file.mkdir();

                if (!wasSuccessful) {
                    System.out.println("file : was not successful");
                }
            }
            for (MultipartFile multipartFile : multipartFiles) {
                String originalFileExtension;
                String contentType = multipartFile.getContentType();

                if (ObjectUtils.isEmpty(contentType)) {
                    break;
                } else {
                    if (contentType.contains("image/jpeg"))
                        originalFileExtension = ".jpg";
                    else if (contentType.contains("image/png"))
                        originalFileExtension = ".png";
                    else  // 다른 확장자일 경우 처리 x
                        break;
                }
                String newFileName = System.nanoTime() + originalFileExtension;

                ImageResponseDto imageResponseDto = ImageResponseDto.builder()
                        .originalFileName(multipartFile.getOriginalFilename())
                        .filePath(path + File.separator + newFileName)
                        .fileSize(multipartFile.getSize())
                        .build();

                Image image = new Image(
                        imageResponseDto.getOriginalFileName(),
                        imageResponseDto.getFilePath(),
                        imageResponseDto.getFileSize()
                );

                imageList.add(image);

                file = new File(absolutePath + path + File.separator + newFileName);
                multipartFile.transferTo(file);

                file.setWritable(true);
                file.setReadable(true);
            }


            }
            return imageList;

        }


    }