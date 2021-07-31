package com.matzipuniv.sinchon.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.matzipuniv.sinchon.domain.Image;
import com.matzipuniv.sinchon.domain.Review;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3UploaderReview {
    private AmazonS3 s3Client;
    public static final String CLOUD_FRONT_DOMAIN_NAME = "d18omhl2ssqffk.cloudfront.net";

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    @PostConstruct
    public void setS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }

    public List<Image> upload(List<MultipartFile> multipartFiles, Review review) throws IOException {
        List<Image> fileList = new ArrayList<>();

        if(multipartFiles.isEmpty()){
            return fileList;
        }

        for (MultipartFile file : multipartFiles) {
            if(!file.isEmpty()) {
                String contentType = file.getContentType();
                String originalFileExtension;

                //확장자명 없을 경우
                if(ObjectUtils.isEmpty(contentType)){
                    break;
                } else{
                    if (contentType.contains("image/jpeg")) {
                        originalFileExtension = ".jpg";
                    } else if (contentType.contains("image/png")) {
                        originalFileExtension = ".png";
                    } else if (contentType.contains("image/gif")) {
                        originalFileExtension = ".gif";
                    }
                    // 다른 파일 명이면 아무 일 하지 않는다
                    else {
                        break;
                    }
                }
                //SimpleDateFormat date = new SimpleDateFormat("yyyymmddHHmmss");
                String fileName = FilenameUtils.getBaseName(file.getOriginalFilename()) + "-" + Long.toString(System.nanoTime()) + "." + FilenameUtils.getExtension(file.getOriginalFilename());

                s3Client.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), null)
                        .withCannedAcl(CannedAccessControlList.PublicRead));

                String filePath = "http://" + CLOUD_FRONT_DOMAIN_NAME + "/" + fileName;
                Image image = new Image(file.getOriginalFilename(), review, filePath, file.getSize());
                fileList.add(image);
            }
        }

        return fileList;
    }

    public String delete(String currentFilePath){
        int idx = currentFilePath.lastIndexOf("/");
        String fileName = currentFilePath.substring(idx+1);
        try {
            s3Client.deleteObject(bucket, fileName);
            return "deleted";
        }
        catch(Exception e) {
            return "error occured" + e.getMessage();
        }
    }

}

